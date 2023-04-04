package kz.tinkoff.homework_2.presentation.mapper

import kz.tinkoff.core.Mapper
import kz.tinkoff.homework_2.domain.model.StreamModel
import kz.tinkoff.homework_2.presentation.delegates.channels.ChannelDelegateItem
import kz.tinkoff.homework_2.presentation.dvo.StreamDvo

class StreamDvoMapper : Mapper<StreamModel, StreamDvo> {

    override fun map(from: StreamModel): StreamDvo {
        return StreamDvo(
            id = from.id,
            name = "#${from.name}",
            topicsDvo = toTopics(from.topics),
            expanded = false,
        )
    }

    fun toChannelDelegate(from: StreamDvo): ChannelDelegateItem {
        return ChannelDelegateItem(from.id, from)
    }

    fun toTopics(from: List<StreamModel.TopicModel>): List<StreamDvo.TopicDvo> {
        return from.map {
            StreamDvo.TopicDvo(
                id = it.id,
                name = "#${it.name}",
            )
        }
    }

    fun toChannelsDelegates(from: List<StreamDvo>): List<ChannelDelegateItem> {
        return from.map { toChannelDelegate(it) }
    }

    fun toChannelsModel(from: List<StreamModel>): List<StreamDvo> {
        val filter = from.map { map(it) }
        return filter
    }

    fun toChannelsDelegateItems(from: List<StreamModel>): List<ChannelDelegateItem> {
        return toChannelsDelegates(toChannelsModel(from))
    }

}