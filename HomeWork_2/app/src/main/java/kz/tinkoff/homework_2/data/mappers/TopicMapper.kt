package kz.tinkoff.homework_2.data.mappers

import kz.tinkoff.core.Mapper
import kz.tinkoff.homework_2.data.model.TopicListResponse
import kz.tinkoff.homework_2.domain.model.StreamModel
import kz.tinkoff.homework_2.domain.model.TopicsModel

class TopicMapper : Mapper<TopicListResponse, TopicsModel> {

    override fun map(from: TopicListResponse): TopicsModel {
        return TopicsModel(topics = from.topics.map { toTopicModel(it) })
    }

    private fun toTopicModel(from: TopicListResponse.TopicResponse): StreamModel.TopicModel {
        return StreamModel.TopicModel(id = from.id, name = from.name)
    }
}