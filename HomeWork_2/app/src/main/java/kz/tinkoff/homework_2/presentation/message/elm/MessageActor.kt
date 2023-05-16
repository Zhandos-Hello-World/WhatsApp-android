package kz.tinkoff.homework_2.presentation.message.elm

import android.text.Html
import com.github.terrakok.cicerone.Router
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kz.tinkoff.core.adapter.DelegateItem
import kz.tinkoff.core.emoji.emojiSetNCS
import kz.tinkoff.coreui.custom.dvo.MessageDvo
import kz.tinkoff.coreui.item.ReactionViewItem
import kz.tinkoff.homework_2.BuildConfig
import kz.tinkoff.homework_2.R
import kz.tinkoff.homework_2.domain.model.EditMessageParams
import kz.tinkoff.homework_2.domain.model.MessageStreamParams
import kz.tinkoff.homework_2.domain.model.ReactionParams
import kz.tinkoff.homework_2.domain.repository.MessageRepository
import kz.tinkoff.homework_2.navigation.Screens
import kz.tinkoff.homework_2.presentation.delegates.message.MessageDelegateItem
import kz.tinkoff.homework_2.presentation.mapper.MessageDelegateItemMapper
import kz.tinkoff.homework_2.presentation.message.MessageArgs
import kz.tinkoff.homework_2.presentation.streams.list.StreamsListArgs
import vivid.money.elmslie.coroutines.Actor

class MessageActor @Inject constructor(
    private val repository: MessageRepository,
    private val delegateItemMapper: MessageDelegateItemMapper,
    private val router: Router,
) : Actor<MessageCommand, MessageEvent> {
    private var messageList: List<DelegateItem> = mutableListOf()
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

                    responseLocally.forEach {
                        repository.deleteMessageLocally(it.id())
                    }

                    repository.saveDataLocally(response)
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
                        val id = repository.sendMessage(
                            params = MessageStreamParams(
                                type = MessageStreamParams.STREAM,
                                to = args.streamId,
                                content = message,
                                topic = args.topic.replace("#", "")
                            )
                        )
                        addMessageAfterSuccess(id, message)
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

                    val message = getMessage(command.position)
                    val emoji =
                        if (isEmojiUnicode(command.emoji)) getEmojiNameFromUnicode(command.emoji)
                            ?: "" else command.emoji
                    repository.addReaction(message.id, ReactionParams(emojiName = emoji))
                }
            }
            is MessageCommand.BackToChannels -> {
                flow {
                    router.backTo(Screens.StreamsScreen())
                }
            }
            is MessageCommand.ItemShowed -> {
                flow { }
            }
            is MessageCommand.DeleteMessage -> {
                flow {
                    val message = getMessage(command.position)
                    val success = repository.deleteMessage(message.id)

                    if (success) {
                        repository.deleteMessageLocally(message.id)
                        messageList = messageList - message
                        emit(MessageEvent.Internal.MessageLoaded(messageList))
                    }
                }
            }
            is MessageCommand.CopyToClipBoardCommand -> {
                flow {
                    val message = getMessage(command.position)
                    val content = parseHtmlValue(message.content().content)
                    emit(MessageEvent.Internal.CopyToClipBoard(content))
                }
            }
            is MessageCommand.ChangeMessageContentCommand -> {
                flow {
                    if (command.content.isEmpty()) {
                        emit(MessageEvent.Internal.ShowToast(R.string.error))
                        return@flow
                    }

                    val message = getMessage(command.position)
                    args?.let { args ->
                        val response = repository.changeMessage(
                            messageId = message.id,
                            params = EditMessageParams(
                                topic = args.topic.replace("#", ""),
                                content = command.content,
                                streamId = args.streamId
                            )
                        )
                        if (response) {
                            emit(MessageEvent.Internal.ShowToast(R.string.changed_successfully))
                            val list = messageList.toMutableList()
                            list[command.position] = MessageDelegateItem(
                                id = message.id,
                                value = message.content().copy(content = command.content)
                            )
                            messageList = list
                            emit(MessageEvent.Internal.MessageLoaded(messageList))
                            emit(MessageEvent.Internal.UpdatePosition(command.position))
                        } else {
                            emit(MessageEvent.Internal.ShowToast(R.string.error))
                        }
                    }
                }.catch {
                    emit(MessageEvent.Internal.ShowToast(R.string.error))
                }
            }
            is MessageCommand.ForwardMessageToTopicCommand -> {
                flow {
                    val message = getMessage(command.position)
                    val response = repository.forwardMessage(
                        messageId = message.id,
                        params = EditMessageParams(
                            topic = command.topicName,
                            content = parseHtmlValue(message.content().content),
                            streamId = command.streamId
                        )
                    )
                    if (response) {
                        messageList = messageList - message
                        emit(MessageEvent.Internal.MessageLoaded(messageList))
                        emit(MessageEvent.Internal.ForwardMessageSuccess)
                    }
                }
            }
            MessageCommand.NavigateToSelectTopicCommand -> {
                flow {
                    val screen = Screens.SubscribedStreamScreen(
                        args = StreamsListArgs(
                            requestType = StreamsListArgs.StreamRequest.SUBSCRIBED,
                            selectTopicWithResultListener = true
                        )
                    )
                    router.navigateTo(screen)
                }
            }
            is MessageCommand.RequestToChangeMessageContentCommand -> {
                flow {
                    val content = parseHtmlValue(getMessage(command.position).content().content)
                    emit(MessageEvent.Internal.GetContent(command.position, content))
                }
            }
        }
    }

    private fun getMessage(position: Int): MessageDelegateItem {
        return messageList[position] as MessageDelegateItem
    }

    private fun parseHtmlValue(html: String): String {
        return Html.fromHtml(html, Html.FROM_HTML_MODE_LEGACY)
            .toString().trim()
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


    private fun addMessageAfterSuccess(id: Int, message: String) {
        val currentTime = System.currentTimeMillis()
        messageList = messageList + delegateItemMapper.toMessageDelegate(
            MessageDvo(
                id = id,
                senderId = BuildConfig.USER_ID.toLong(),
                timestamp = currentTime,
                isMeMessage = true,
                reactions = mutableListOf(),
                senderFullName = BuildConfig.FULL_NAME,
                content = message,
                avatarUrl = BuildConfig.AVATAR
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
        const val MAX_LOAD = 1000
        const val MIN_LOAD = 0
    }

}
