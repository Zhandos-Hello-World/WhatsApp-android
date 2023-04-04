package kz.tinkoff.homework_2.presentation.delegates.message

import kz.tinkoff.core.adapter.DelegateItem
import kz.tinkoff.coreui.custom.dvo.MessageDvo

class MessageDelegateItem(
    val id: Int,
    private val value: MessageDvo,
) : DelegateItem {

    override fun content(): MessageDvo = value

    override fun id(): Int = value.id

}