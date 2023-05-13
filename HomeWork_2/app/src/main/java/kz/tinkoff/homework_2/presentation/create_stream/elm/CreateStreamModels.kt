package kz.tinkoff.homework_2.presentation.create_stream.elm

sealed interface CreateStreamState {

    object NotInit : CreateStreamState

    object Loading : CreateStreamState

    object Error : CreateStreamState

    object Success : CreateStreamState
}

sealed interface CreateStreamEvent {

    sealed interface Ui : CreateStreamEvent {

        object NotInit : Ui

        object BackToStreams : Ui

        data class CreateStreamRequest(val name: String, val desc: String) : Ui

    }

    sealed class Internal : CreateStreamEvent {

        object CreateStreamSuccess : Internal()

        object ErrorLoading : Internal()
    }
}

sealed interface CreateStreamEffect {
    object CreateStreamError : CreateStreamEffect
}

sealed interface CreateStreamCommand {

    data class CreateStreamRequest(val name: String, val desc: String) : CreateStreamCommand

    object BackToStreams : CreateStreamCommand

}
