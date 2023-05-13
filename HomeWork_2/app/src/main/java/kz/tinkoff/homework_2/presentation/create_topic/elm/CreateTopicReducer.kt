package kz.tinkoff.homework_2.presentation.create_topic.elm

import vivid.money.elmslie.core.store.dsl_reducer.DslReducer

class CreateTopicReducer :
    DslReducer<CreateTopicEvent, CreateTopicState, CreateTopicEffects, CreateTopicCommand>() {

    override fun Result.reduce(event: CreateTopicEvent): Any {
        return when (event) {
            CreateTopicEvent.Internal.CreateTopicError -> {
                effects { +CreateTopicEffects.CreateTopicError }
            }
            CreateTopicEvent.Internal.CreateTopicLoading -> {
                state { CreateTopicState.CreateTopicLoading }
            }
            CreateTopicEvent.Internal.CreateTopicSuccess -> {
                state { CreateTopicState.CreateTopicSuccess }
            }
            is CreateTopicEvent.Internal.ReplaceByMessage -> {
                commands { +CreateTopicCommand.ReplaceByMessage(event.args) }
            }
            is CreateTopicEvent.Ui.CreateTopicRequest -> {
                commands {
                    +CreateTopicCommand.CreateTopicRequest(
                        topicName = event.topicName,
                        firstMessage = event.firstMessage
                    )
                }
            }
            is CreateTopicEvent.Ui.Init -> {
                commands { +CreateTopicCommand.Init(event.args) }
            }
            CreateTopicEvent.Ui.BackToStreams -> {
                commands { +CreateTopicCommand.BackToStreams }
            }
        }
    }
}
