package kz.tinkoff.homework_2.presentation.channels.elm

import vivid.money.elmslie.core.store.dsl_reducer.DslReducer

class ChannelReducer : DslReducer<ChannelEvent, ChannelState, ChannelEffect, ChannelCommand>() {

    override fun Result.reduce(event: ChannelEvent): Any? {
        return when (event) {
            is ChannelEvent.Internal.ChannelLoaded -> {
                state { copy(channels = event.data, error = false, isLoading = false) }
            }
            is ChannelEvent.Internal.ErrorLoading -> {
                state { copy(channels = emptyList(), error = true, isLoading = false) }
            }
            is ChannelEvent.Ui.LoadChannel -> {
                state { copy(channels = emptyList(), error = false, isLoading = true) }
                commands { +ChannelCommand.LoadChannel }
            }
            is ChannelEvent.Ui.NavigateToMessage -> {
                commands { +ChannelCommand.NavigateToMessageCommand(event.args) }
            }
            is ChannelEvent.Ui.SearchChannel -> {
                commands { +ChannelCommand.SearchChannelCommand(event.text) }
            }
        }
    }
}