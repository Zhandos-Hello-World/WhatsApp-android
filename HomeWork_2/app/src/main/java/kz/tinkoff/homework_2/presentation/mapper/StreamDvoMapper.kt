package kz.tinkoff.homework_2.presentation.mapper

import kz.tinkoff.core.Mapper
import kz.tinkoff.coreui.R
import kz.tinkoff.homework_2.domain.model.StreamModel
import kz.tinkoff.homework_2.presentation.delegates.channels.StreamDelegateItem
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

    private fun toChannelDelegate(from: StreamDvo): StreamDelegateItem {
        return StreamDelegateItem(from.id, from)
    }

    fun toTopics(from: List<StreamModel.TopicModel>): List<StreamDvo.TopicDvo> {
        return from.mapIndexed { index, from ->
            StreamDvo.TopicDvo(
                id = from.id,
                name = "#${from.name}",
                color = if (index % 2 == 0) R.color.yellow else R.color.green
            )
        }
    }

    fun toChannelsDelegates(from: List<StreamDvo>): List<StreamDelegateItem> {
        return from.map { toChannelDelegate(it) }
    }

    private fun toChannelsModel(from: List<StreamModel>): List<StreamDvo> {
        val filter = from.map { map(it) }
        return filter
    }

    fun toChannelsDelegateItems(from: List<StreamModel>): List<StreamDelegateItem> {
        return toChannelsDelegates(toChannelsModel(from))
    }

}
