package kz.tinkoff.homework_2.data.mappers

import kz.tinkoff.core.Mapper
import kz.tinkoff.homework_2.data.model.ChannelListResponse
import kz.tinkoff.homework_2.domain.model.ChannelModel

class ChannelMapper : Mapper<ChannelListResponse.ChannelResponse, ChannelModel> {

    override fun map(from: ChannelListResponse.ChannelResponse): ChannelModel {
        return ChannelModel(id = from.id, name = from.name, topics = toTopics(from.topics))
    }

    fun toListChannel(from: ChannelListResponse?): List<ChannelModel> {
        return from?.list?.map { map(it) }.orEmpty()
    }

    private fun toTopics(from: List<ChannelListResponse.TopicResponse>): List<ChannelModel.TopicModel> {
        val list = from.map { from ->
            ChannelModel.TopicModel(id = from.id, name = from.name, count = from.messageCount)
        }
        return list
    }
}