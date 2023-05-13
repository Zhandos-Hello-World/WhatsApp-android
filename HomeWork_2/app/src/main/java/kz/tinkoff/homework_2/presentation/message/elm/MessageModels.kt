package kz.tinkoff.homework_2.presentation.message.elm

import kz.tinkoff.core.adapter.DelegateItem
import kz.tinkoff.homework_2.presentation.message.MessageArgs

sealed interface MessageState {

    data class Data(val messageDvo: List<DelegateItem>) : MessageState

    data class UpdatedPosition(val position: Int): MessageState

    object Error : MessageState

    object Loading : MessageState
}

sealed class MessageEvent {

    sealed class Ui : MessageEvent() {
        data class LoadMessages(val args: MessageArgs) : Ui()

        data class ItemShowed(val position: Int) : Ui()

        data class AddReaction(
            val position: Int,
            val emoji: String,
        ) : Ui()

        data class DeleteReaction(
            val position: Int,
            val emoji: String,
        ) : Ui()

        data class AddMessage(val message: String) : Ui()

        object BackToStreams : Ui()

    }

    sealed class Internal : MessageEvent() {
        data class UpdatePosition(val position: Int) : Internal()

        data class MessageLoaded(val data: List<DelegateItem>) : Internal()

        object ErrorLoading : Internal()
    }
}

class MessageEffect

sealed class MessageCommand {
    data class LoadMessages(val args: MessageArgs) : MessageCommand()
    data class ItemShowed(val position: Int) : MessageCommand()
    data class AddReaction(val position: Int, val emoji: String) :
        MessageCommand()

    data class DeleteReaction(val position: Int, val emoji: String) :
        MessageCommand()

    data class AddMessage(val message: String) : MessageCommand()
    object BackToChannels : MessageCommand()
}
