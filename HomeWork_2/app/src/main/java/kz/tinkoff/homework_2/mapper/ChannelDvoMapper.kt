package kz.tinkoff.homework_2.mapper

import kz.tinkoff.homework_2.presentation.delegates.channels.ChannelDelegateItem
import kz.tinkoff.homework_2.presentation.delegates.channels.ChannelModel

class ChannelDvoMapper {

    fun toChannelDelegate(from: ChannelModel): ChannelDelegateItem {
        return ChannelDelegateItem(
            from.id,
            from
        )
    }

    fun toChannelsDelegates(from: List<ChannelModel>): List<ChannelDelegateItem> {
        return from.map { toChannelDelegate(it) }
    }
}