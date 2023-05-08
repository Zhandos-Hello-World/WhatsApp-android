package kz.tinkoff.homework_2.presentation.message.elm

import android.util.Log
import com.github.terrakok.cicerone.Router
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kz.tinkoff.core.adapter.DelegateItem
import kz.tinkoff.core.emoji.emojiSetNCS
import kz.tinkoff.coreui.custom.dvo.MessageDvo
import kz.tinkoff.coreui.item.ReactionViewItem
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
                    var responseLocally = getMessagesLocally(command.args)
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
                    Log.d(
                        "equalsResponse",
                        (responseLocally == getMessagesLocally(command.args)).toString()
                    )
                    responseLocally = getMessagesLocally(command.args)
                    if (responseLocally.isNotEmpty()) {
                        messageList = responseLocally
                        emit(MessageEvent.Internal.MessageLoaded(messageList))
                    }
                }.catch {
                    emit(MessageEvent.Internal.ErrorLoading)
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
                    changeMessage(command.position, command.emoji)
                    emit(MessageEvent.Internal.MessageLoaded(messageList))
                    emit(MessageEvent.Internal.UpdatePosition(command.position))

                    val message = messageList[command.position] as MessageDelegateItem
                    val emoji =
                        if (isEmojiUnicode(command.emoji)) getEmojiNameFromUnicode(command.emoji)
                            ?: "" else command.emoji
                    repository.deleteReaction(message.id, ReactionParams(emojiName = emoji))
                }
            }
            is MessageCommand.AddReaction -> {
                flow {
                    changeMessage(command.position, command.emoji)
                    emit(MessageEvent.Internal.MessageLoaded(messageList))
                    emit(MessageEvent.Internal.UpdatePosition(command.position))

                    val message = messageList[command.position] as MessageDelegateItem
                    val emoji =
                        if (isEmojiUnicode(command.emoji)) getEmojiNameFromUnicode(command.emoji)
                            ?: "" else command.emoji
                    repository.addReaction(message.id, ReactionParams(emojiName = emoji))
                }
            }
            is MessageCommand.BackToChannels -> {
                flow {
                    router.backTo(Screens.ChannelsScreen())
                }
            }
            is MessageCommand.ItemShowed -> {
                flow { }
            }
        }
    }

    private fun changeMessage(position: Int, emoji: String) {
        val model = messageList[position] as MessageDelegateItem
        val reactions = model.content().reactions

        val isEmojiUnicode = isEmojiUnicode(emoji)

        val emojiDvo = if (isEmojiUnicode) {
            emoji
        } else {
            getEmojiUnicodeFromName(emoji) ?: return
        }

        val reaction = reactions.find { it.emoji == emojiDvo }
        if (reaction != null) {
            val reactionIndex = reactions.indexOf(reaction)
            if (reaction.fromMe) {
                if (reaction.count == 1) {
                    reactions.removeAt(reactionIndex)
                } else {
                    reactions[reactionIndex] =
                        reaction.copy(count = reaction.count - 1, fromMe = false)
                }
            } else {
                reactions[reactionIndex] = reaction.copy(count = reaction.count + 1, fromMe = true)
            }
        } else {
            reactions.add(
                ReactionViewItem(
                    reactions.hashCode(),
                    count = 1,
                    emoji = emojiDvo,
                    fromMe = true
                ),
            )
        }
    }

    private fun isEmojiUnicode(value: String): Boolean {
        val character = try {
            value[0]
        } catch (ex: Exception) {
            return false
        }
        val type = Character.getType(character)
        return type == Character.SURROGATE.toInt() || type == Character.OTHER_SYMBOL.toInt()
    }

    private fun getEmojiUnicodeFromName(emojiName: String): String? {
        return emojiSetNCS.find { it.name == emojiName }?.getCodeString()
    }

    private fun getEmojiNameFromUnicode(code: String): String? {
        return emojiSetNCS.find { it.getCodeString() == code }?.name
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

    private suspend fun getMessagesLocally(args: MessageArgs): List<DelegateItem> {
        val messagesResponse = repository.getAllMessageLocally(
            args.stream.replace("#", ""),
            args.topic.replace("#", "")
        )
        return delegateItemMapper.toMessageWithDateFromModel(messagesResponse)
    }

    companion object {
        const val NOT_FOUND_INDEX = -1

        const val LAST_LEFT_ITEMS = 5
        const val LOAD_ITEMS = 20

        const val MAX_LOAD = 1000
        const val MIN_LOAD = 0
    }

}
