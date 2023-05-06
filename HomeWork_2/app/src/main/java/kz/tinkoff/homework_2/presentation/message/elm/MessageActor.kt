package kz.tinkoff.homework_2.presentation.message.elm

import com.github.terrakok.cicerone.Router
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kz.tinkoff.core.adapter.DelegateItem
import kz.tinkoff.coreui.custom.dvo.MessageDvo
import kz.tinkoff.homework_2.domain.model.MessageModel
import kz.tinkoff.homework_2.domain.model.MessageStreamParams
import kz.tinkoff.homework_2.domain.model.ReactionParams
import kz.tinkoff.homework_2.domain.repository.MessageRepository
import kz.tinkoff.homework_2.navigation.Screens
import kz.tinkoff.homework_2.presentation.delegates.message.MessageDelegateItem
import kz.tinkoff.homework_2.presentation.mapper.MessageDelegateItemMapper
import kz.tinkoff.homework_2.presentation.message.MessageArgs
import vivid.money.elmslie.coroutines.Actor

class MessageActor @Inject constructor(
    private val repository: MessageRepository,
    private val delegateItemMapper: MessageDelegateItemMapper,
    private val router: Router,
) : Actor<MessageCommand, MessageEvent> {
    private var messageList: List<DelegateItem> = mutableListOf<DelegateItem>()
    private val numBefore get() = if (messageList.size < LOAD_ITEMS) LOAD_ITEMS else messageList.size
    private val numAfter get() = numBefore - 20
    private var args: MessageArgs? = null

    override fun execute(command: MessageCommand): Flow<MessageEvent> {
        return when (command) {
            is MessageCommand.LoadMessages -> {
                flow<MessageEvent> {
                    args = command.args
                    var responseLocally = getMessagesLocally(command.args).first()
                    if (responseLocally.isNotEmpty()) {
                        messageList = responseLocally
                        emit(MessageEvent.Internal.MessageLoaded(messageList))
                    }

                    val response = repository.getAllMessage(
                        streamId = command.args.streamId,
                        stream = command.args.stream.replace("#", ""),
                        topic = command.args.topic.replace("#", ""),
                        numBefore = MAX_LOAD,
                        numAfter = MIN_LOAD
                    )
                    repository.saveDataLocally(response)
                    responseLocally = getMessagesLocally(command.args).first()
                    if (responseLocally.isNotEmpty()) {
                        messageList = responseLocally
                        emit(MessageEvent.Internal.MessageLoaded(messageList))
                    }
                }
            }
            is MessageCommand.AddMessage -> {
                flow {
                    args?.let { args ->
                        val message = command.message
                        repository.sendMessage(
                            params = MessageStreamParams(
                                type = MessageStreamParams.STREAM,
                                to = args.streamId,
                                content = message,
                                topic = args.topic.replace("#", "")
                            )
                        )
                        addMessageAfterSuccess(message)
                        emit(MessageEvent.Internal.MessageLoaded(messageList))
                    }
                }
            }
            is MessageCommand.DeleteReaction -> {
                flow {
                    val model = messageList[command.position] as MessageDelegateItem
                    val emoji = command.emoji
                    repository.deleteReaction(
                        messageId = model.id,
                        params = ReactionParams(
                            emojiName = emoji,
                        ),
                    )
                    repository.updateMessage(model.id, emoji)
                    args?.let { args ->
                        messageList = getMessagesLocally(args).first()
                        emit(MessageEvent.Internal.MessageLoaded(messageList))
                        emit(MessageEvent.Internal.UpdatePosition(command.position))
                    }
                }
            }
            is MessageCommand.AddReaction -> {
                flow {
                    val model = messageList[command.position] as MessageDelegateItem
                    val emoji = command.emoji
                    repository.addReaction(
                        messageId = model.id,
                        params = ReactionParams(
                            emojiName = emoji,
                        ),
                    )
                    repository.updateMessage(model.id, emoji)
                    args?.let { args ->
                        messageList = getMessagesLocally(args).first()
                        emit(MessageEvent.Internal.MessageLoaded(messageList))
                        emit(MessageEvent.Internal.UpdatePosition(command.position))
                    }
                }
            }
            is MessageCommand.BackToChannels -> {
                flow {
                    router.backTo(Screens.ChannelsScreen())
                }
            }
            is MessageCommand.ItemShowed -> {
                TODO()
            }
        }
    }

    private fun addMessageAfterSuccess(message: String) {
        val currentTime = System.currentTimeMillis()
        messageList = messageList + delegateItemMapper.toMessageDelegate(
            MessageDvo(
                id = currentTime.toInt(),
                senderId = 0,
                timestamp = currentTime,
                isMeMessage = true,
                reactions = mutableListOf(),
                senderFullName = "Baimurat Zhandos",
                content = message,
                avatarUrl = ""
            )
        )
    }

    private fun getMessagesLocally(args: MessageArgs): Flow<List<DelegateItem>> {
        val messagesResponse = repository.getAllMessageLocally(
            args.stream.replace("#", ""),
            args.topic.replace("#", "")
        )
        return messagesResponse.map { delegateItemMapper.toMessageWithDateFromModel(it) }
    }

    companion object {
        const val NOT_FOUND_INDEX = -1

        const val LAST_LEFT_ITEMS = 5
        const val LOAD_ITEMS = 20

        const val MAX_LOAD = 1000
        const val MIN_LOAD = 0
    }

}
