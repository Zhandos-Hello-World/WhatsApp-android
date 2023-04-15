package kz.tinkoff.homework_2.data.repository

import kz.tinkoff.homework_2.data.mappers.StreamMapper
import kz.tinkoff.homework_2.data.mappers.TopicMapper
import kz.tinkoff.homework_2.domain.datasource.ChannelRemoteDataSource
import kz.tinkoff.homework_2.domain.model.StreamModel
import kz.tinkoff.homework_2.domain.model.TopicsModel
import kz.tinkoff.homework_2.domain.repository.ChannelRepository

// Стандартный нейминг RepoNameImpl
class DefaultChannelRepository(
    private val dataSource: ChannelRemoteDataSource,
    private val streamMapper: StreamMapper,
    private val topicMapper: TopicMapper
) : ChannelRepository {

    override suspend fun getAllChannels(): List<StreamModel> {
        return streamMapper.toListChannel(dataSource.getAllChannels())
    }

    override suspend fun findChannels(name: String): List<StreamModel> {
        return streamMapper.toListChannel(dataSource.findChannels(name))
    }

    override suspend fun findTopics(id: Int): TopicsModel {
        return topicMapper.map(dataSource.findTopics(id))
    }
}
