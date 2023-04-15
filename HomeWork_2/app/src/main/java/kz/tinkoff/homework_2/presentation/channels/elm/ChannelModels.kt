package kz.tinkoff.homework_2.presentation.channels.elm

import kz.tinkoff.homework_2.presentation.delegates.channels.ChannelDelegateItem
import kz.tinkoff.homework_2.presentation.message.MessageArgs

// Мнене очень нравитсья такой подход для стейтов, т.к. он потенциально может содержать
// Неконсистентное состояние. Как будем обрабатывать, если error = true, isLoading = true,
// channels != emptyList()? Можно было оставить подход, как я показывал на семинаре
data class ChannelState(
    val channels: List<ChannelDelegateItem> = emptyList(),
    val error: Boolean = false,
    val isLoading: Boolean = false,
)

sealed class ChannelEvent {

    sealed class Ui : ChannelEvent() {

        object LoadChannel : Ui()

        data class SearchChannel(val text: String): Ui()

        data class NavigateToMessage(val args: MessageArgs): ChannelEvent()

    }

    sealed class Internal : ChannelEvent() {

        data class ChannelLoaded(val data: List<ChannelDelegateItem>) : Internal()

        object ErrorLoading : Internal()
    }
}

class ChannelEffect

sealed class ChannelCommand {
    object LoadChannel : ChannelCommand()
    data class NavigateToMessageCommand(val args: MessageArgs): ChannelCommand()
    data class SearchChannelCommand(val text: String): ChannelCommand()

}
