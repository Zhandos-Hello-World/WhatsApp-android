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
        data class ItemShowed(val args: MessageArgs, val position: Int) : Ui()

        data class LoadMessageRemote(val args: MessageArgs) : Ui()

        data class LoadMessageRemoteSilently(val args: MessageArgs) : Ui()

        data class LoadMessageLocal(val args: MessageArgs) : Ui()

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

        data class MessageLoadedRemote(val data: List<DelegateItem>) : Internal()

        data class MessageLoadedLocal(val data: List<DelegateItem>) : Internal()

        object ErrorLoading : Internal()
    }
}

class MessageEffect

sealed class MessageCommand {
    data class ItemShowed(val args: MessageArgs, val position: Int) : MessageCommand()
    data class LoadMessageRemoteSilently(val args: MessageArgs) : MessageCommand()
    data class LoadMessageRemote(val args: MessageArgs) : MessageCommand()
    data class LoadMessageLocal(val args: MessageArgs) : MessageCommand()
    data class AddReaction(val args: MessageArgs, val model: MessageDvo, val emoji: String) :
        MessageCommand()

    data class DeleteReaction(val args: MessageArgs, val model: MessageDvo, val emoji: String) :
        MessageCommand()

    data class AddMessage(val args: MessageArgs, val message: String) : MessageCommand()
    object BackToChannels : MessageCommand()
}
