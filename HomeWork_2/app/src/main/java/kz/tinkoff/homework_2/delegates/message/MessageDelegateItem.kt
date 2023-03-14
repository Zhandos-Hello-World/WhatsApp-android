package kz.tinkoff.homework_2.delegates.message

import kz.tinkoff.core.adapter.DelegateItem

class MessageDelegateItem(
    val id: Int,
    private val value: MessageModel,
) : DelegateItem<MessageModel> {

    override fun content(): MessageModel = value

    override fun id(): Int = id

    override fun compareToOther(other: DelegateItem<MessageModel>): Boolean {
        return other.content() == value && other.content().reactions == value.reactions
    }
}