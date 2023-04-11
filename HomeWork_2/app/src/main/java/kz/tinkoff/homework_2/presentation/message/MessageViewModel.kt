package kz.tinkoff.homework_2.presentation.message

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.terrakok.cicerone.Router
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kz.tinkoff.core.adapter.DelegateItem
import kz.tinkoff.core.ktx.runCatchingNonCancellation
import kz.tinkoff.coreui.ScreenState
import kz.tinkoff.coreui.custom.dvo.MessageDvo
import kz.tinkoff.coreui.item.ReactionViewItem
import kz.tinkoff.homework_2.domain.model.MessageStreamParams
import kz.tinkoff.homework_2.domain.model.MessageStreamParams.Companion.STREAM
import kz.tinkoff.homework_2.domain.model.ReactionParams
import kz.tinkoff.homework_2.domain.repository.MessageRepository
import kz.tinkoff.homework_2.navigation.Screens
import kz.tinkoff.homework_2.presentation.delegates.message.MessageDelegateItem
import kz.tinkoff.homework_2.presentation.mapper.MessageDvoMapper

class MessageViewModel(
    private val repository: MessageRepository,
    private val messageDvoMapper: MessageDvoMapper,
    private val router: Router,
) : ViewModel() {
    private val _messageList =
        MutableStateFlow<ScreenState<List<DelegateItem>>>(ScreenState.Loading)
    val messageList: StateFlow<ScreenState<List<DelegateItem>>> get() = _messageList.asStateFlow()
    var args: MessageArgs? = null

    fun getMessage() {
        viewModelScope.launch {
            var state: ScreenState<List<DelegateItem>> = ScreenState.Loading
            _messageList.emit(state)

            val messageArgs = args
            val result = runCatchingNonCancellation {
                if (messageArgs != null) {
                    repository.getAllMessage(
                        streamId = messageArgs.streamId,
                        stream = messageArgs.stream.replace("#", ""),
                        topic = messageArgs.topic.replace("#", "")
                    )
                } else {
                    null
                }
            }.getOrNull()


            state = if (result != null) {
                ScreenState.Data(messageDvoMapper.toMessageWithDateFromModel(result))
            } else {
                ScreenState.Error
            }

            _messageList.emit(state)
        }
    }

    fun updateDelegate(model: MessageDvo, emoji: String) {
        addReaction(model, emoji)
    }

    fun updateDelegate(model: MessageDvo, emoji: ReactionViewItem) {
        if (emoji.fromMe) {
            deleteReaction(model, emoji.emoji)
        } else {
            addReaction(model, emoji.emoji)
        }
    }

    private fun addReaction(model: MessageDvo, emoji: String) {
        viewModelScope.launch {
            val response = runCatchingNonCancellation {
                repository.addReaction(
                    messageId = model.id,
                    params = ReactionParams(
                        emojiName = emoji,
                    ),
                )
            }.getOrNull()

            if (response == true) {
                addReactionAfterSuccess(model, emoji)
            }
        }
    }

    private fun deleteReaction(model: MessageDvo, emoji: String) {
        viewModelScope.launch {
            val response = runCatchingNonCancellation {
                repository.deleteReaction(
                    messageId = model.id,
                    params = ReactionParams(
                        emojiName = emoji,
                    ),
                )
            }.getOrNull()

            if (response == true) {
                deleteReactionAfterSuccess(model, emoji)
            }
        }
    }


    fun addMessage(message: String) {
        viewModelScope.launch {
            val messageArgs = args

            val response = runCatchingNonCancellation {
                if (messageArgs != null) {
                    repository.sendMessage(
                        params = MessageStreamParams(
                            type = STREAM,
                            to = messageArgs.streamId,
                            content = message,
                            topic = messageArgs.topic.replace("#", "")
                        )
                    )
                } else {
                    null
                }
            }.getOrNull()

            if (response == true) {
                addMessageAfterSuccess(message)
            }
        }
    }

    private suspend fun addMessageAfterSuccess(message: String) {
        (_messageList.value as? ScreenState.Data<List<DelegateItem>>)?.let { state ->
            val currentTime = System.currentTimeMillis()
            val listMessages = state.data + messageDvoMapper.toMessageDelegate(
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
            _messageList.emit(ScreenState.Data(listMessages))
        }
    }

    private suspend fun deleteReactionAfterSuccess(model: MessageDvo, emoji: String) {
        (_messageList.value as? ScreenState.Data<List<DelegateItem>>)?.let { state ->
            val message = findMessageValue(state.data, model) ?: return
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
            _messageList.emit(state)
        }
    }

    private suspend fun addReactionAfterSuccess(model: MessageDvo, emoji: String) {
        (_messageList.value as? ScreenState.Data<List<DelegateItem>>)?.let { state ->
            val message = findMessageValue(state.data, model) ?: return
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
            _messageList.emit(state)
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

    fun backToChannels() {
        router.backTo(Screens.ChannelsScreen())
    }

    companion object {
        const val NOT_FOUND_INDEX = -1
    }
}