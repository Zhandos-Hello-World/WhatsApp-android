package kz.tinkoff.homework_2.presentation.mapper

import kz.tinkoff.core.Mapper
import kz.tinkoff.homework_2.domain.model.ChannelModel
import kz.tinkoff.homework_2.presentation.delegates.channels.ChannelDelegateItem
import kz.tinkoff.homework_2.presentation.dvo.ChannelDvo

class ChannelDvoMapper : Mapper<ChannelModel, ChannelDvo> {

    override fun map(from: ChannelModel): ChannelDvo {
        return ChannelDvo(
            id = from.id,
            name = from.name,
            topicsDvo = toTopics(from.topics),
            expanded = false,
        )
    }

    fun toChannelDelegate(from: ChannelDvo): ChannelDelegateItem {
        return ChannelDelegateItem(from.id, from)
    }

    fun toTopics(from: List<ChannelModel.TopicModel>): List<ChannelDvo.TopicDvo> {
        return from.map {
            ChannelDvo.TopicDvo(
                id = it.id,
                name = it.name,
                count = it.count,
            )
        }
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