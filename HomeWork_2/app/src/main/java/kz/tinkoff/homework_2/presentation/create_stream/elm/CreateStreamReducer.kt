package kz.tinkoff.homework_2.presentation.create_stream.elm

import vivid.money.elmslie.core.store.dsl_reducer.DslReducer

class CreateStreamReducer :
    DslReducer<CreateStreamEvent, CreateStreamState, CreateStreamEffect, CreateStreamCommand>() {

    override fun Result.reduce(event: CreateStreamEvent): Any {
        return when (event) {
            is CreateStreamEvent.Internal.CreateStreamSuccess -> {
                state { CreateStreamState.Success }
            }
            is CreateStreamEvent.Internal.ErrorLoading -> {
                state { CreateStreamState.Error }
                effects { +CreateStreamEffect.CreateStreamError }
            }
            is CreateStreamEvent.Ui.CreateStreamRequest -> {
                state { CreateStreamState.Loading }
                commands { +CreateStreamCommand.CreateStreamRequest(event.name, event.desc) }
            }
            CreateStreamEvent.Ui.BackToStreams -> {
                commands { +CreateStreamCommand.BackToStreams }
            }
            CreateStreamEvent.Ui.NotInit -> {}
        }
    }
}
