package kz.tinkoff.homework_2.presentation.streams.elm

import kz.tinkoff.homework_2.presentation.delegates.channels.StreamDelegateItem
import kz.tinkoff.homework_2.presentation.dvo.StreamDvo
import kz.tinkoff.homework_2.presentation.message.MessageArgs
import kz.tinkoff.homework_2.presentation.streams.list.StreamsListArgs

sealed interface StreamState {

    object Error : StreamState

    object Loading : StreamState

    data class Data(val channels: List<StreamDelegateItem>) : StreamState

    data class UpdatePosition(val position: Int, val expanded: Boolean) : StreamState
}

sealed class StreamEvent {

    sealed class Ui : StreamEvent() {

        data class LoadStream(val args: StreamsListArgs) : Ui()

        data class SearchStream(val text: String) : Ui()

        data class NavigateToMessage(val args: MessageArgs) : StreamEvent()

        data class LoadTopic(val streamId: Int) : Ui()

        data class NavigateToCreateTopic(val dvo: StreamDvo, val position: Int) : Ui()

        data class AddTopicToStream(val dvo: StreamDvo): Ui()
    }

    sealed class Internal : StreamEvent() {

        data class StreamLoaded(val data: List<StreamDelegateItem>) : Internal()

        object ErrorLoading : Internal()

        data class UpdatePosition(val position: Int, val expanded: Boolean) : Internal()

        data class CreateTopic(val dvo: StreamDvo, val position: Int) : Internal()
    }
}

sealed interface StreamEffect {

    data class CreateTopic(val dvo: StreamDvo, val position: Int) : StreamEffect
}

sealed class StreamCommand {
    data class LoadStream(val args: StreamsListArgs) : StreamCommand()
    data class LoadTopic(val streamId: Int) : StreamCommand()
    data class SearchStreamCommand(val text: String) : StreamCommand()

    data class NavigateToCreateTopic(val dvo: StreamDvo, val position: Int) : StreamCommand()
    data class NavigateToMessage(val args: MessageArgs) : StreamCommand()
    data class AddTopicToStream(val dvo: StreamDvo): StreamCommand()

}
