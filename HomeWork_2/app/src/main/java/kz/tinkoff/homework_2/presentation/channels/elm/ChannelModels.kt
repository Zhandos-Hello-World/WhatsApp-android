package kz.tinkoff.homework_2.presentation.channels.elm

import kz.tinkoff.homework_2.presentation.delegates.channels.ChannelDelegateItem
import kz.tinkoff.homework_2.presentation.message.MessageArgs

sealed interface ChannelState {

    object Error : ChannelState

    object Loading : ChannelState

    data class Data(val channels: List<ChannelDelegateItem>) : ChannelState
}

sealed class ChannelEvent {

    sealed class Ui : ChannelEvent() {

        object LoadChannel : Ui()

        data class SearchChannel(val text: String) : Ui()

        data class NavigateToMessage(val args: MessageArgs) : ChannelEvent()

    }

    sealed class Internal : ChannelEvent() {

        data class ChannelLoaded(val data: List<ChannelDelegateItem>) : Internal()

        object ErrorLoading : Internal()
    }
}

class ChannelEffect

sealed class ChannelCommand {
    object LoadChannel : ChannelCommand()
    data class SearchChannelCommand(val text: String) : ChannelCommand()

}
