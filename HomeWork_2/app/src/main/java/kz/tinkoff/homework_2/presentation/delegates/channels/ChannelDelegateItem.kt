package kz.tinkoff.homework_2.presentation.delegates.channels

import kz.tinkoff.core.adapter.DelegateItem

class ChannelDelegateItem(private val id: Int, private val value: ChannelDvo): DelegateItem {

    override fun content(): ChannelDvo = value

    override fun id(): Int = value.id
}