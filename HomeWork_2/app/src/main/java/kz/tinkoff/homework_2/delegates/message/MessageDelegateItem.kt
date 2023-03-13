package kz.tinkoff.homework_2.delegates.message

import kz.tinkoff.core.adapter.DelegateItem

class MessageDelegateItem(
    val id: Int,
    private val value: MessageModel,
) : DelegateItem {

    override fun content(): Any = value

    override fun id(): Int = id

    override fun compareToOther(other: DelegateItem): Boolean {
        val other = (other as MessageDelegateItem)
        val equals = other.value == value && other.value.reactions == value.reactions
        return equals
    }
}