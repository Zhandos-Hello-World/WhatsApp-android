package kz.tinkoff.homework_2.presentation.streams.elm

import com.github.terrakok.cicerone.Router
import kz.tinkoff.homework_2.navigation.Screens
import vivid.money.elmslie.core.store.dsl_reducer.DslReducer

class StreamReducer(private val router: Router) :
    DslReducer<StreamEvent, StreamState, StreamEffect, StreamCommand>() {

    override fun Result.reduce(event: StreamEvent): Any? {
        return when (event) {
            is StreamEvent.Internal.StreamLoaded -> {
                state { StreamState.Data(event.data) }
            }
            is StreamEvent.Internal.ErrorLoading -> {
                state { StreamState.Error }
            }
            is StreamEvent.Ui.LoadStream -> {
                state { StreamState.Loading }
                commands { +StreamCommand.LoadStream(event.args) }
            }
            is StreamEvent.Ui.NavigateToMessage -> {
                router.navigateTo(Screens.MessageScreen(event.args))
            }
            is StreamEvent.Ui.SearchStream -> {
                commands { +StreamCommand.SearchStreamCommand(event.text) }
            }
            is StreamEvent.Internal.UpdatePosition -> {
                state { StreamState.UpdatePosition(event.position, event.expanded) }
            }
            is StreamEvent.Ui.LoadTopic -> {
                commands { +StreamCommand.LoadTopic(event.streamId) }
            }
        }
    }
}
