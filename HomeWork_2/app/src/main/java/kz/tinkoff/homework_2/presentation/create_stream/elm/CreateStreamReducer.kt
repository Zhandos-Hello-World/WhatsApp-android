package kz.tinkoff.homework_2.presentation.create_stream.elm

import com.github.terrakok.cicerone.Router
import kz.tinkoff.homework_2.navigation.Screens
import vivid.money.elmslie.core.store.dsl_reducer.DslReducer

class CreateStreamReducer constructor(private val router: Router) :
    DslReducer<CreateStreamEvent, CreateStreamState, CreateStreamEffect, CreateStreamCommand>() {

    override fun Result.reduce(event: CreateStreamEvent): Any {
        return when (event) {
            is CreateStreamEvent.Internal.CreateStreamSuccess -> {
                state { CreateStreamState.Success }
            }
            is CreateStreamEvent.Internal.ErrorLoading -> {
                state { CreateStreamState.Error }
                effects { CreateStreamEffect.CreateStreamError }
            }
            is CreateStreamEvent.Ui.CreateStreamRequest -> {
                state { CreateStreamState.Loading }
                commands { +CreateStreamCommand.CreateStreamRequest(event.name, event.desc) }
            }
            CreateStreamEvent.Ui.NotInit -> {

            }
            CreateStreamEvent.Ui.BackToStreams -> {
                router.navigateTo(Screens.StreamsScreen())
            }
        }
    }
}
