package kz.tinkoff.homework_2.presentation.message.elm

import vivid.money.elmslie.core.store.dsl_reducer.DslReducer

class MessageReducer : DslReducer<MessageEvent, MessageState, MessageEffect, MessageCommand>() {

    override fun Result.reduce(event: MessageEvent) = when (event) {
        is MessageEvent.Ui.LoadMessages -> {
            state { MessageState.Loading }
            commands { +MessageCommand.LoadMessages(event.args) }
        }
        is MessageEvent.Ui.AddMessage -> {
            commands { +MessageCommand.AddMessage(event.message) }
        }
        is MessageEvent.Ui.AddReaction -> {
            commands { +MessageCommand.AddReaction(event.position, event.emoji) }
        }
        is MessageEvent.Ui.DeleteReaction -> {
            commands { +MessageCommand.DeleteReaction(event.position, event.emoji) }
        }
        is MessageEvent.Ui.ItemShowed -> {
            commands { +MessageCommand.ItemShowed(event.position) }
        }
        is MessageEvent.Ui.BackToStreams -> {
            commands { +MessageCommand.BackToChannels }
        }
        is MessageEvent.Ui.CopyToClipBoardEvent -> {
            commands { +MessageCommand.CopyToClipBoardCommand(event.position) }
        }
        is MessageEvent.Ui.DeleteMessage -> {
            commands { +MessageCommand.DeleteMessage(event.position) }
        }
        is MessageEvent.Internal.MessageLoaded -> {
            state { MessageState.Data(event.data) }
        }
        is MessageEvent.Internal.ErrorLoading -> {
            state { MessageState.Error }
        }
        is MessageEvent.Internal.UpdatePosition -> {
            state { MessageState.UpdatedPosition(event.position) }
        }
        is MessageEvent.Internal.CopyToClipBoard -> {
            effects { +MessageEffect.CopyToClipBoardEffect(event.text) }
        }
        is MessageEvent.Ui.ChangeMessageContentEvent -> {
            commands { +MessageCommand.ChangeMessageContentCommand(event.position, event.content) }
        }
        is MessageEvent.Internal.GetContent -> {
            effects { +MessageEffect.MessageChangeEffect(event.position, event.content) }
        }
        is MessageEvent.Ui.RequestToChangeMessageContentEvent -> {
            commands { +MessageCommand.RequestToChangeMessageContentCommand(event.position) }
        }
        is MessageEvent.Ui.ForwardMessageToTopicEvent -> {
            commands {
                +MessageCommand.ForwardMessageToTopicCommand(
                    position = event.position,
                    topicName = event.topicName,
                    streamId = event.streamId
                )
            }
        }
        MessageEvent.Ui.SelectTopicEvent -> {
            commands { +MessageCommand.NavigateToSelectTopicCommand }
        }
        MessageEvent.Internal.ForwardMessageSuccess -> {
            effects { +MessageEffect.MessageForwardToTopicEffect }
        }
        is MessageEvent.Internal.ShowToast -> {
            effects { +MessageEffect.ShowToastEffect(event.id) }
        }
    }
}
