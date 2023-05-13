package kz.tinkoff.homework_2.presentation.create_topic.elm

import kz.tinkoff.homework_2.presentation.dvo.StreamDvo
import kz.tinkoff.homework_2.presentation.message.MessageArgs

sealed interface CreateTopicState {

    object NotInit: CreateTopicState

    object CreateTopicSuccess : CreateTopicState

    object CreateTopicLoading : CreateTopicState
}

sealed interface CreateTopicEvent {

    sealed interface Ui : CreateTopicEvent {

        data class CreateTopicRequest(
            val topicName: String,
            val firstMessage: String,
        ) : Ui

        data class Init(val args: StreamDvo) : Ui

        object BackToStreams: CreateTopicEvent
    }

    sealed interface Internal : CreateTopicEvent {

        object CreateTopicSuccess : Internal

        object CreateTopicError : Internal

        object CreateTopicLoading : Internal

        data class ReplaceByMessage(val args: MessageArgs): Internal
    }
}

sealed interface CreateTopicCommand {
    data class CreateTopicRequest(
        val topicName: String,
        val firstMessage: String,
    ) : CreateTopicCommand

    data class Init(val args: StreamDvo): CreateTopicCommand

    data class ReplaceByMessage(val args: MessageArgs): CreateTopicCommand

    object BackToStreams: CreateTopicCommand

}

sealed interface CreateTopicEffects {
    object CreateTopicError: CreateTopicEffects
}
