package kz.tinkoff.homework_2.presentation.message.elm

import kz.tinkoff.core.adapter.DelegateItem
import kz.tinkoff.coreui.custom.dvo.MessageDvo
import kz.tinkoff.homework_2.presentation.message.MessageArgs

sealed interface MessageState {

    data class Data(val messageDvo: List<DelegateItem>) : MessageState

    object Error : MessageState

    object Loading: MessageState
}

sealed class MessageEvent {

    sealed class Ui : MessageEvent() {

        data class LoadMessage(val args: MessageArgs) : Ui()

        data class AddReaction(
            val args: MessageArgs,
            val model: MessageDvo,
            val emoji: String,
        ) : Ui()

        data class DeleteReaction(
            val args: MessageArgs,
            val model: MessageDvo,
            val emoji: String,
        ) : Ui()

        data class AddMessage(val args: MessageArgs, val message: String) : Ui()

        object BackToChannels : Ui()

    }

    sealed class Internal : MessageEvent() {

        data class MessageLoaded(val data: List<DelegateItem>) : Internal()

        object ErrorLoading : Internal()
    }
}

class MessageEffect

sealed class MessageCommand {
    data class LoadMessage(val args: MessageArgs) : MessageCommand()
    data class AddReaction(val args: MessageArgs, val model: MessageDvo, val emoji: String) :
        MessageCommand()

    data class DeleteReaction(val args: MessageArgs, val model: MessageDvo, val emoji: String) :
        MessageCommand()

    data class AddMessage(val args: MessageArgs, val message: String) : MessageCommand()
    object BackToChannels : MessageCommand()
}
