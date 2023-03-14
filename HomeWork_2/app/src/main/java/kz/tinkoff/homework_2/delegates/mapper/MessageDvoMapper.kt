package kz.tinkoff.homework_2.delegates.mapper

import kz.tinkoff.core.Mapper
import kz.tinkoff.core.adapter.DelegateItem
import kz.tinkoff.coreui.custom.dvo.MessageDvo
import kz.tinkoff.homework_2.delegates.date.DateDelegateItem
import kz.tinkoff.homework_2.delegates.date.DateModel
import kz.tinkoff.homework_2.delegates.message.MessageDelegateItem
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

    fun toMessageWithData(from: List<MessageModel>): List<DelegateItem> {
        if (from.isEmpty()) {
            return emptyList()
        }

        val delegateItemList: MutableList<DelegateItem> = mutableListOf()
        var date = from[0].date
        delegateItemList.add(DateDelegateItem(DateModel(date)))


        from.forEach { messageModel ->
            if (messageModel.date != date) {
                date = messageModel.date
                delegateItemList.add(DateDelegateItem(DateModel(date)))
            }
            delegateItemList.add(MessageDelegateItem(id = messageModel.id, value = messageModel))
        }
        return delegateItemList
    }
}