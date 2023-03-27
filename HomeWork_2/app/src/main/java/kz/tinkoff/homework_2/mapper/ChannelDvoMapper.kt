package kz.tinkoff.homework_2.mapper

import kz.tinkoff.core.Mapper
import kz.tinkoff.homework_2.domain.model.ChannelModel
import kz.tinkoff.homework_2.presentation.delegates.channels.ChannelDelegateItem
import kz.tinkoff.homework_2.presentation.delegates.channels.ChannelDvo

class ChannelDvoMapper : Mapper<ChannelModel, ChannelDvo> {

    override fun map(from: ChannelModel): ChannelDvo {
        return ChannelDvo(
            id = from.id,
            name = from.name,
            testingMessageCount = from.testingMessageCount,
            brushMessageCount = from.brushMessageCount,
            expanded = false
        )
    }

    fun toChannelDelegate(from: ChannelDvo): ChannelDelegateItem {
        return ChannelDelegateItem(
            from.id,
            from
        )
    }

    fun toChannelsDelegates(from: List<ChannelDvo>): List<ChannelDelegateItem> {
        return from.map { toChannelDelegate(it) }
    }

    fun toChannelsModel(from: List<ChannelModel>): List<ChannelDvo> {
        val filter = from.map { map(it) }
        return filter
    }

    fun toChannelsDelegateItems(from: List<ChannelModel>): List<ChannelDelegateItem> {
        return toChannelsDelegates(toChannelsModel(from))
    }


}