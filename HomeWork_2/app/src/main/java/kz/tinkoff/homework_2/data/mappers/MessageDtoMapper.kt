package kz.tinkoff.homework_2.data.mappers

import kz.tinkoff.core.Mapper
import kz.tinkoff.homework_2.domain.model.MessageStreamParams

class MessageDtoMapper: Mapper<MessageStreamParams, HashMap<String, Any?>> {

    override fun map(from: MessageStreamParams): HashMap<String, Any?> {
        val filters = hashMapOf<String, Any?>()

        filters[TYPE] = from.type
        filters[CONTENT] = from.content
        filters[TO] = from.to
        filters[TOPIC] = from.topic
        return filters
    }

    // private
    companion object {
        const val TYPE = "type"
        const val CONTENT = "content"
        const val TO = "to"
        const val TOPIC = "topic"
    }
}
