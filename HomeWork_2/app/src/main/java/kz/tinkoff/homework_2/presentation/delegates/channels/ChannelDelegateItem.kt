package kz.tinkoff.homework_2.presentation.delegates.channels

import kz.tinkoff.core.adapter.DelegateItem
import kz.tinkoff.homework_2.presentation.dvo.StreamDvo

class ChannelDelegateItem(private val id: Int, private val value: StreamDvo): DelegateItem {

    override fun content(): StreamDvo = value

    override fun id(): Int = value.id
}