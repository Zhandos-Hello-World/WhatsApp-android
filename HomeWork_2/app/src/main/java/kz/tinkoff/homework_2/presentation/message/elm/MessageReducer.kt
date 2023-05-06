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
        is MessageEvent.Ui.BackToChannels -> {
            commands { +MessageCommand.BackToChannels }
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
    }
}
