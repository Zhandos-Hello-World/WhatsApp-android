package kz.tinkoff.homework_2.data.repository

import kz.tinkoff.homework_2.data.mappers.StreamMapper
import kz.tinkoff.homework_2.data.mappers.TopicMapper
import kz.tinkoff.homework_2.domain.datasource.ChannelRemoteDataSource
import kz.tinkoff.homework_2.domain.model.StreamModel
import kz.tinkoff.homework_2.domain.model.TopicsModel
import kz.tinkoff.homework_2.domain.repository.ChannelRepository

class RepoStreamImpl(
    private val dataSource: ChannelRemoteDataSource,
    private val streamMapper: StreamMapper,
    private val topicMapper: TopicMapper,
) : ChannelRepository {

    override suspend fun getAllChannels(): List<StreamModel> {
        return streamMapper.toListChannel(dataSource.getAllStreams())
    }

    override suspend fun findChannels(name: String): List<StreamModel> {
        return streamMapper.toListChannel(dataSource.findStreams(name))
    }

    override suspend fun findTopics(id: Int): TopicsModel {
        return topicMapper.map(dataSource.findTopics(id))
    }
}