package kz.tinkoff.homework_2.presentation.message.elm

import com.github.terrakok.cicerone.Router
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kz.tinkoff.core.adapter.DelegateItem
import kz.tinkoff.core.ktx.runCatchingNonCancellation
import kz.tinkoff.coreui.custom.dvo.MessageDvo
import kz.tinkoff.coreui.item.ReactionViewItem
import kz.tinkoff.homework_2.domain.model.MessageStreamParams
import kz.tinkoff.homework_2.domain.model.ReactionParams
import kz.tinkoff.homework_2.domain.repository.MessageRepository
import kz.tinkoff.homework_2.navigation.Screens
import kz.tinkoff.homework_2.presentation.delegates.message.MessageDelegateItem
import kz.tinkoff.homework_2.presentation.mapper.MessageDelegateItemMapper
import vivid.money.elmslie.coroutines.Actor

class MessageActor(
    private val repository: MessageRepository,
    private val delegateItemMapper: MessageDelegateItemMapper,
    private val router: Router,
) : Actor<MessageCommand, MessageEvent> {
    private var messageList = mutableListOf<DelegateItem>()

    override fun execute(command: MessageCommand): Flow<MessageEvent> {
        return when (command) {
            is MessageCommand.LoadMessage -> {
                flow {

                    val messageArgs = command.args
                    val response = runCatchingNonCancellation {
                        repository.getAllMessage(
                            streamId = messageArgs.streamId,
                            stream = messageArgs.stream.replace("#", ""),
                            topic = messageArgs.topic.replace("#", "")
                        )
                    }.getOrNull()

                    if (response != null) {
                        messageList = delegateItemMapper.toMessageWithDateFromModel(
                            response
                        ).toMutableList()
                        emit(
                            MessageEvent.Internal.MessageLoaded(messageList)
                        )
                    } else {
                        emit(MessageEvent.Internal.ErrorLoading)
                    }
                }
            }
            is MessageCommand.AddMessage -> {
                flow {
                    val messageArgs = command.args
                    val message = command.message
                    val response = runCatchingNonCancellation {
                        repository.sendMessage(
                            params = MessageStreamParams(
                                type = MessageStreamParams.STREAM,
                                to = messageArgs.streamId,
                                content = message,
                                topic = messageArgs.topic.replace("#", "")
                            )
                        )
                    }.getOrNull()

                    addMessageAfterSuccess(message)
                    emit(MessageEvent.Internal.MessageLoaded(messageList))
                }
            }
            is MessageCommand.DeleteReaction -> {
                flow {
                    val model = command.model
                    val emoji = command.emoji
                    val response = runCatchingNonCancellation {
                        repository.deleteReaction(
                            messageId = model.id,
                            params = ReactionParams(
                                emojiName = emoji,
                            ),
                        )
                    }.getOrNull()

                    deleteReactionAfterSuccess(model, emoji)
                    emit(MessageEvent.Internal.MessageLoaded(messageList))

                }
            }
            is MessageCommand.AddReaction -> {
                flow {
                    val model = command.model
                    val emoji = command.emoji

                    val response = runCatchingNonCancellation {
                        repository.addReaction(
                            messageId = model.id,
                            params = ReactionParams(
                                emojiName = emoji,
                            ),
                        )
                    }.getOrNull()

                    addReactionAfterSuccess(model, emoji)
                    emit(MessageEvent.Internal.MessageLoaded(messageList))
                }
            }
            is MessageCommand.BackToChannels -> {
                flow {
                    router.backTo(Screens.ChannelsScreen())
                }
            }
        }
    }

    private fun addMessageAfterSuccess(message: String) {
        val currentTime = System.currentTimeMillis()
        messageList += delegateItemMapper.toMessageDelegate(
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

    private fun deleteReactionAfterSuccess(model: MessageDvo, emoji: String) {
        val message = findMessageValue(messageList, model) ?: return
        val reactionIndex = findReactionIndex(message.content(), emoji)

        if (reactionIndex != NOT_FOUND_INDEX) {
            val reaction = message.content().reactions[reactionIndex]
            if (reaction.count - 1 == 0) {
                message.content().reactions.remove(reaction)
            } else {
                message.content().reactions[reactionIndex] =
                    reaction.copy(count = reaction.count - 1)
            }
        }
    }

    private fun addReactionAfterSuccess(model: MessageDvo, emoji: String) {
        val message = findMessageValue(messageList, model) ?: return
        val reactionIndex = findReactionIndex(message.content(), emoji)

        if (reactionIndex != NOT_FOUND_INDEX) {
            val reaction = message.content().reactions[reactionIndex]
            message.content().reactions[reactionIndex] =
                reaction.copy(count = reaction.count + 1)
        } else {
            message.content().reactions.add(
                ReactionViewItem(
                    id = System.currentTimeMillis().toInt(),
                    count = 1,
                    emoji = emoji,
                    fromMe = true
                )
            )
        }
    }

    private fun findMessageValue(
        messageList: List<DelegateItem>,
        model: MessageDvo,
    ): MessageDelegateItem? {
        val messageIndex = messageList.toMutableList().indexOfFirst { it.id() == model.id }
        return messageList[messageIndex] as? MessageDelegateItem
    }

    private fun findReactionIndex(message: MessageDvo, emoji: String): Int {
        return message.reactions.indexOfFirst { it.emoji == emoji }
    }

    companion object {
        const val NOT_FOUND_INDEX = -1

    }

}