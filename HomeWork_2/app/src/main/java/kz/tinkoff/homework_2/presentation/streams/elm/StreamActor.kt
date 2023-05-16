package kz.tinkoff.homework_2.presentation.streams.elm

import com.github.terrakok.cicerone.Router
import javax.inject.Inject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kz.tinkoff.core.ktx.runCatchingNonCancellation
import kz.tinkoff.homework_2.domain.repository.StreamRepository
import kz.tinkoff.homework_2.navigation.Screens
import kz.tinkoff.homework_2.presentation.dvo.StreamDvo
import kz.tinkoff.homework_2.presentation.mapper.StreamDvoMapper
import kz.tinkoff.homework_2.presentation.mapper.SubscribedStreamDvoMapper
import kz.tinkoff.homework_2.presentation.streams.list.StreamsListArgs
import vivid.money.elmslie.coroutines.Actor

class StreamActor @Inject constructor(
    private val repository: StreamRepository,
    private val streamDvoMapper: StreamDvoMapper,
    private val subscribedDvoMapper: SubscribedStreamDvoMapper,
    private val coroutineScope: CoroutineScope,
    private val router: Router,
) : Actor<StreamCommand, StreamEvent> {
    private var args: StreamsListArgs? = null
    private var streams = listOf<StreamDvo>()

    override fun execute(command: StreamCommand): Flow<StreamEvent> = when (command) {
        is StreamCommand.LoadStream -> {
            flow<StreamEvent> {
                args = command.args
                streams = when (command.args.requestType) {
                    StreamsListArgs.StreamRequest.ALL_STREAMS -> {
                        getAllStreams()
                    }
                    StreamsListArgs.StreamRequest.SUBSCRIBED -> {
                        getSubscribedStreams()
                    }
                }
                emit(
                    StreamEvent.Internal.StreamLoaded(
                        streamDvoMapper.toChannelsDelegates(streams)
                    )
                )
            }.catch {
                emit(StreamEvent.Internal.ErrorLoading)
            }
        }
        is StreamCommand.SearchStreamCommand -> {
            flow<StreamEvent> {
                val response = repository.findChannels(command.text)

                emit(
                    StreamEvent.Internal.StreamLoaded(
                        streamDvoMapper.toChannelsDelegateItems(response)
                    )
                )
            }.catch {
                emit(StreamEvent.Internal.ErrorLoading)
            }
        }
        is StreamCommand.LoadTopic -> {
            flow {
                val streamDvo = streams.find { it.id == command.streamId } ?: return@flow

                if (!streamDvo.expanded && streamDvo.topicsDvo.isEmpty()) {
                    val topics = getTopicsByIdAsync(streamDvo.id).await()?.topics.orEmpty()
                    streamDvo.topicsDvo = streamDvoMapper.toTopics(topics)
                }
                streamDvo.expanded = !streamDvo.expanded

                if (streamDvo.topicsDvo.isEmpty() && streamDvo.expanded) {
                    emit(
                        StreamEvent.Internal.CreateTopic(
                            dvo = streamDvo,
                            position = streams.indexOf(streamDvo)
                        )
                    )
                } else {
                    emit(
                        StreamEvent.Internal.UpdatePosition(
                            position = streams.indexOf(streamDvo),
                            expanded = streamDvo.expanded
                        )
                    )
                }

            }
        }
        is StreamCommand.NavigateToCreateTopic -> {
            flow {
                router.navigateTo(
                    Screens.CreateTopicScreen(
                        command.dvo
                    )
                )
            }
        }
        is StreamCommand.NavigateToMessage -> {
            flow {
                router.navigateTo(Screens.MessageScreen(command.args))
            }
        }
        is StreamCommand.AddTopicToStream -> {
            flow {
                router.navigateTo(
                    Screens.CreateTopicScreen(
                        command.dvo
                    )
                )
            }
        }
        StreamCommand.BackToMessageCommand -> {
            flow {
                router.exit()
            }
        }
    }

    private suspend fun getAllStreams(): List<StreamDvo> {
        return repository.getAllStreams().map { streamDvoMapper.map(it) }
    }

    private suspend fun getSubscribedStreams(): List<StreamDvo> {
        return repository.getSubscribedStreams().map { subscribedDvoMapper.map(it) }
    }

    private fun getTopicsByIdAsync(id: Int) = coroutineScope.async {
        runCatchingNonCancellation {
            repository.findTopics(id)
        }.getOrNull()
    }
}
