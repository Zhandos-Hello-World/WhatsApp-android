package kz.tinkoff.homework_2.presentation.channels.elm

import javax.inject.Inject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kz.tinkoff.core.ktx.runCatchingNonCancellation
import kz.tinkoff.homework_2.domain.repository.StreamRepository
import kz.tinkoff.homework_2.presentation.mapper.StreamDvoMapper
import vivid.money.elmslie.coroutines.Actor

class StreamActor @Inject constructor(
    private val repository: StreamRepository,
    private val dvoMapper: StreamDvoMapper,
    private val coroutineScope: CoroutineScope,
) : Actor<ChannelCommand, ChannelEvent> {

    override fun execute(command: ChannelCommand): Flow<ChannelEvent> = when (command) {
        is ChannelCommand.LoadChannel -> {
            flow<ChannelEvent> {
                val responseStreams = repository.getAllChannels()

                val topicDeferred =
                    responseStreams.map { channelModel -> getTopicsByIdAsync(channelModel.id) }

                val responseTopics = topicDeferred.awaitAll().filterNotNull()
                responseStreams.forEachIndexed { index, streamModel ->
                    streamModel.topics = responseTopics[index].topics
                }

                emit(
                    ChannelEvent.Internal.ChannelLoaded(
                        dvoMapper.toChannelsDelegateItems(
                            responseStreams
                        )
                    )
                )
            }.catch {
                emit(ChannelEvent.Internal.ErrorLoading)
            }
        }
        is ChannelCommand.SearchChannelCommand -> {
            flow<ChannelEvent> {
                val response = repository.findChannels(command.text)

                emit(
                    ChannelEvent.Internal.ChannelLoaded(
                        dvoMapper.toChannelsDelegateItems(response)
                    )
                )
            }.catch {
                emit(ChannelEvent.Internal.ErrorLoading)
            }
        }
    }

    private fun getTopicsByIdAsync(id: Int) = coroutineScope.async {
        runCatchingNonCancellation {
            repository.findTopics(id)
        }.getOrNull()
    }
}
