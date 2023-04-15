package kz.tinkoff.homework_2.presentation.channels.elm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.terrakok.cicerone.Router
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kz.tinkoff.core.ktx.runCatchingNonCancellation
import kz.tinkoff.homework_2.domain.repository.ChannelRepository
import kz.tinkoff.homework_2.navigation.Screens
import kz.tinkoff.homework_2.presentation.mapper.StreamDvoMapper
import vivid.money.elmslie.coroutines.Actor

// Почему актор наследует ViewModel?
class ChannelActor(
    private val repository: ChannelRepository,
    private val dvoMapper: StreamDvoMapper,
    private val router: Router,
) : ViewModel(), Actor<ChannelCommand, ChannelEvent> {

    override fun execute(command: ChannelCommand): Flow<ChannelEvent> = when (command) {
        is ChannelCommand.LoadChannel -> {
            flow<ChannelEvent> {
                // Во флоу можно без runCatchingNonCancellation в большинстве случаев
                // Ошибки обрабатываются при помощи оператора catch
                val responseStreams = runCatchingNonCancellation {
                    repository.getAllChannels()
                }.getOrNull()

                if (responseStreams == null) {
                    emit(ChannelEvent.Internal.ErrorLoading)
                    return@flow
                }
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
            }
        }
        // Это не в actor, а в обработчике Ui Event
        is ChannelCommand.NavigateToMessageCommand -> {
            flow {
                router.navigateTo(Screens.MessageScreen(command.args))
            }
        }
        is ChannelCommand.SearchChannelCommand -> {
            flow {
                // МОжно убрать задержки
                delay(500L)

                val response =
                    runCatchingNonCancellation { repository.findChannels(command.text) }.getOrNull()

                if (response != null) {
                    emit(
                        ChannelEvent.Internal.ChannelLoaded(
                            dvoMapper.toChannelsDelegateItems(response)
                        )
                    )
                } else {
                    emit(ChannelEvent.Internal.ErrorLoading)
                }
            }
        }
    }

    private fun getTopicsByIdAsync(id: Int) = viewModelScope.async {
        runCatchingNonCancellation {
            repository.findTopics(id)
        }.getOrNull()
    }

}
