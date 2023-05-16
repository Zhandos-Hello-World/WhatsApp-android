package kz.tinkoff.homework_2.presentation.message.elm

import androidx.annotation.StringRes
import kz.tinkoff.core.adapter.DelegateItem
import kz.tinkoff.homework_2.presentation.message.MessageArgs

sealed interface MessageState {

    data class Data(val messageDvo: List<DelegateItem>) : MessageState

    data class UpdatedPosition(val position: Int) : MessageState

    object Error : MessageState

    object Loading : MessageState
}

sealed interface MessageEvent {

    sealed interface Ui : MessageEvent {
        data class DeleteMessage(val position: Int) : Ui

        data class LoadMessages(val args: MessageArgs) : Ui

        data class ItemShowed(val position: Int) : Ui

        data class AddReaction(
            val position: Int,
            val emoji: String,
        ) : Ui

        data class DeleteReaction(
            val position: Int,
            val emoji: String,
        ) : Ui

        data class AddMessage(val message: String) : Ui

        data class CopyToClipBoardEvent(val position: Int) : Ui

        data class ForwardMessageToTopicEvent(
            val position: Int,
            val topicName: String,
            val streamId: Int,
        ) : Ui

        data class ChangeMessageContentEvent(val position: Int, val content: String) : Ui

        data class RequestToChangeMessageContentEvent(val position: Int) : Ui

        object SelectTopicEvent : Ui

        object BackToStreams : Ui
    }

    sealed interface Internal : MessageEvent {
        data class UpdatePosition(val position: Int) : Internal

        data class MessageLoaded(val data: List<DelegateItem>) : Internal

        data class CopyToClipBoard(val text: String) : Internal

        data class GetContent(val position: Int, val content: String) : Internal

        data class ShowToast(@StringRes val id: Int) : Internal

        object ForwardMessageSuccess : Internal

        object ErrorLoading : Internal
    }
}

sealed interface MessageEffect {

    data class CopyToClipBoardEffect(val text: String) : MessageEffect

    object MessageForwardToTopicEffect : MessageEffect

    data class MessageChangeEffect(val position: Int, val content: String) : MessageEffect

    data class ShowToastEffect(@StringRes val id: Int) : MessageEffect

}

sealed interface MessageCommand {

    data class LoadMessages(val args: MessageArgs) : MessageCommand

    data class ItemShowed(val position: Int) : MessageCommand

    data class AddReaction(val position: Int, val emoji: String) : MessageCommand

    data class DeleteReaction(val position: Int, val emoji: String) : MessageCommand

    data class AddMessage(val message: String) : MessageCommand

    data class DeleteMessage(val position: Int) : MessageCommand

    data class CopyToClipBoardCommand(val position: Int) : MessageCommand

    data class ForwardMessageToTopicCommand(
        val position: Int,
        val topicName: String,
        val streamId: Int,
    ) : MessageCommand

    data class ChangeMessageContentCommand(val position: Int, val content: String) : MessageCommand

    data class RequestToChangeMessageContentCommand(val position: Int) : MessageCommand

    object BackToChannels : MessageCommand

    object NavigateToSelectTopicCommand : MessageCommand

}
