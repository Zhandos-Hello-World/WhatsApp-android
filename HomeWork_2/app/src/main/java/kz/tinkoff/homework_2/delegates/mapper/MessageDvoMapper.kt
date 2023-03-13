package kz.tinkoff.homework_2.delegates.mapper

import kz.tinkoff.core.Mapper
import kz.tinkoff.coreui.custom.dvo.MessageDvo
import kz.tinkoff.homework_2.delegates.message.MessageModel

class MessageDvoMapper : Mapper<MessageModel, MessageDvo> {

    override fun map(from: MessageModel): MessageDvo {
        return MessageDvo(
            id = from.id,
            title = from.fullName,
            message = from.message,
            date = from.date,
            reactions = from.reactions,
        )
    }

    fun toMessageModel(from: MessageDvo): MessageModel {
        return MessageModel(
            id = from.id,
            fullName = from.title,
            message = from.message,
            date = from.date,
            reactions = from.reactions
        )
    }
}