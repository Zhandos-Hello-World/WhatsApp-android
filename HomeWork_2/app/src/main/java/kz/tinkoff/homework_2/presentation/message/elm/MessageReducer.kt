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
        is MessageEvent.Ui.LoadMessageLocal -> {
            commands { +MessageCommand.LoadMessageLocal(event.args) }
        }
        is MessageEvent.Ui.LoadMessageRemote -> {
            state { MessageState.Loading }
            commands { +MessageCommand.LoadMessageRemote(event.args) }
        }
        is MessageEvent.Ui.ItemShowed -> {
            commands { +MessageCommand.ItemShowed(event.args, event.position) }
        }
        is MessageEvent.Ui.LoadMessageRemoteSilently -> {
            commands { +MessageCommand.LoadMessageRemoteSilently(event.args) }
        }
        is MessageEvent.Ui.BackToChannels -> {
            commands { +MessageCommand.BackToChannels }
        }
        is MessageEvent.Internal.MessageLoadedRemote -> {
            state { MessageState.Data(event.data) }
        }
        is MessageEvent.Internal.MessageLoadedLocal -> {
            if (event.data.isEmpty()) {
                state { MessageState.Loading }
            } else {
                state { MessageState.Data(event.data) }
            }
        }
        is MessageEvent.Internal.ErrorLoading -> {
            state { MessageState.Error }
        }

    }
}
