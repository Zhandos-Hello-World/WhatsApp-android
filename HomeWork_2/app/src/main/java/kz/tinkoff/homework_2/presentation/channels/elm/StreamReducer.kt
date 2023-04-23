package kz.tinkoff.homework_2.presentation.channels.elm

import com.github.terrakok.cicerone.Router
import kz.tinkoff.homework_2.navigation.Screens
import vivid.money.elmslie.core.store.dsl_reducer.DslReducer

class StreamReducer(private val router: Router) :
    DslReducer<ChannelEvent, StreamState, ChannelEffect, ChannelCommand>() {

    override fun Result.reduce(event: ChannelEvent): Any? {
        return when (event) {
            is ChannelEvent.Internal.ChannelLoaded -> {
                state { StreamState.Data(event.data) }
            }
            is ChannelEvent.Internal.ErrorLoading -> {
                state { StreamState.Error }
            }
            is ChannelEvent.Ui.LoadChannel -> {
                state { StreamState.Loading }
                commands { +ChannelCommand.LoadChannel }
            }
            is ChannelEvent.Ui.NavigateToMessage -> {
                router.navigateTo(Screens.MessageScreen(event.args))
            }
            is ChannelEvent.Ui.SearchChannel -> {
                commands { +ChannelCommand.SearchChannelCommand(event.text) }
            }
        }
    }
}
