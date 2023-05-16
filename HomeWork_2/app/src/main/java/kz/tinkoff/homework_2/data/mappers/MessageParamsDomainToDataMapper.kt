package kz.tinkoff.homework_2.data.mappers

import kz.tinkoff.core.Mapper
import kz.tinkoff.homework_2.domain.model.MessageStreamParams

class MessageParamsDomainToDataMapper: Mapper<MessageStreamParams, HashMap<String, Any?>> {

    override fun map(from: MessageStreamParams): HashMap<String, Any?> {
        val filters = hashMapOf<String, Any?>()

        filters[TYPE] = from.type
        filters[CONTENT] = from.content
        filters[TO] = from.to
        filters[TOPIC] = from.topic
        return filters
    }

    companion object {
        private const val TYPE = "type"
        private const val CONTENT = "content"
        private const val TO = "to"
        private const val TOPIC = "topic"
    }
}
