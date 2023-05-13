package kz.tinkoff.homework_2.presentation.streams.elm

import vivid.money.elmslie.core.store.dsl_reducer.DslReducer

class StreamReducer :
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
                commands { +StreamCommand.NavigateToMessage(event.args) }
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
            is StreamEvent.Internal.CreateTopic -> {
                effects { +StreamEffect.CreateTopic(event.dvo, event.position) }
            }
            is StreamEvent.Ui.NavigateToCreateTopic -> {
                commands {
                    +StreamCommand.NavigateToCreateTopic(
                        dvo = event.dvo,
                        position = event.position
                    )
                }
            }
        }
    }
}
