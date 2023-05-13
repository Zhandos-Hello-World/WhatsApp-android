package kz.tinkoff.homework_2.presentation.create_topic.elm

import com.github.terrakok.cicerone.Router
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kz.tinkoff.homework_2.domain.model.MessageStreamParams
import kz.tinkoff.homework_2.domain.model.MessageStreamParams.Companion.STREAM
import kz.tinkoff.homework_2.domain.repository.MessageRepository
import kz.tinkoff.homework_2.navigation.Screens
import kz.tinkoff.homework_2.presentation.dvo.StreamDvo
import kz.tinkoff.homework_2.presentation.message.MessageArgs
import vivid.money.elmslie.coroutines.Actor

class CreateTopicActor @Inject constructor(
    private val repository: MessageRepository,
    private val router: Router,
) :
    Actor<CreateTopicCommand, CreateTopicEvent> {
    private var args: StreamDvo? = null

    override fun execute(command: CreateTopicCommand): Flow<CreateTopicEvent> {
        return when (command) {
            is CreateTopicCommand.Init -> {
                flow<CreateTopicEvent> {
                    args = command.args
                }
            }
            is CreateTopicCommand.CreateTopicRequest -> {
                flow<CreateTopicEvent> {
                    args?.let { streamDvo ->
                        if (command.topicName.isEmpty() || command.firstMessage.isEmpty()) {
                            emit(CreateTopicEvent.Internal.CreateTopicError)
                            return@flow
                        }

                        val response = repository.sendMessage(
                            MessageStreamParams(
                                type = STREAM,
                                to = streamDvo.id,
                                content = command.firstMessage,
                                topic = command.topicName
                            )
                        )
                        if (response) {
                            emit(
                                CreateTopicEvent.Internal.ReplaceByMessage(
                                    args = MessageArgs(
                                        streamId = streamDvo.id,
                                        stream = streamDvo.name.replace("#", ""),
                                        topic = command.topicName
                                    )
                                )
                            )
                        } else {
                            emit(CreateTopicEvent.Internal.CreateTopicError)
                        }
                    }
                }.catch {
                    emit(CreateTopicEvent.Internal.CreateTopicError)
                }
            }
            CreateTopicCommand.BackToStreams -> {
                flow {
                    router.backTo(Screens.StreamsScreen())
                }
            }
            is CreateTopicCommand.ReplaceByMessage -> {
                flow {
                    router.replaceScreen(Screens.MessageScreen(command.args))
                }
            }
        }
    }
}
