package kz.tinkoff.homework_2.presentation.message.elm

import vivid.money.elmslie.core.store.dsl_reducer.DslReducer

class MessageReducer : DslReducer<MessageEvent, MessageState, MessageEffect, MessageCommand>() {

    override fun Result.reduce(event: MessageEvent) = when (event) {
        is MessageEvent.Ui.AddMessage -> {
            commands { +MessageCommand.AddMessage(event.args, event.message) }
        }
        is MessageEvent.Ui.AddReaction -> {
            commands { +MessageCommand.AddReaction(event.args, event.model, event.emoji) }
        }
        is MessageEvent.Ui.DeleteReaction -> {
            commands { +MessageCommand.DeleteReaction(event.args, event.model, event.emoji) }
        }
        is MessageEvent.Ui.LoadMessage -> {
            state { MessageState.Loading }
            commands { +MessageCommand.LoadMessage(event.args) }
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

    }
}
