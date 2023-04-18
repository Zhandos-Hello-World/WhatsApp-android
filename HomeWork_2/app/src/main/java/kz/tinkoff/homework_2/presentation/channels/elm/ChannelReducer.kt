package kz.tinkoff.homework_2.presentation.channels.elm

import com.github.terrakok.cicerone.Router
import kz.tinkoff.core.utils.lazyUnsafe
import kz.tinkoff.homework_2.navigation.Screens
import vivid.money.elmslie.core.store.dsl_reducer.DslReducer

class ChannelReducer(private val router: Router) :
    DslReducer<ChannelEvent, ChannelState, ChannelEffect, ChannelCommand>() {

    override fun Result.reduce(event: ChannelEvent): Any? {
        return when (event) {
            is ChannelEvent.Internal.ChannelLoaded -> {
                state { ChannelState.Data(event.data) }
            }
            is ChannelEvent.Internal.ErrorLoading -> {
                state { ChannelState.Error }
            }
            is ChannelEvent.Ui.LoadChannel -> {
                state { ChannelState.Loading }
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
