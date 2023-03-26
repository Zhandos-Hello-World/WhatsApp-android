package kz.tinkoff.homework_2.presentation.delegates.channels

import kz.tinkoff.core.adapter.DelegateItem

class ChannelDelegateItem(private val id: Int, private val value: ChannelModel): DelegateItem {

    override fun content(): ChannelModel = value

    override fun id(): Int = value.id
}