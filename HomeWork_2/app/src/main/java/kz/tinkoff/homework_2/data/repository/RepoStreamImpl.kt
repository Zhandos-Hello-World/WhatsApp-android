package kz.tinkoff.homework_2.data.repository

import javax.inject.Inject
import kz.tinkoff.homework_2.data.mappers.StreamMapper
import kz.tinkoff.homework_2.data.mappers.TopicMapper
import kz.tinkoff.homework_2.domain.datasource.StreamRemoteDataSource
import kz.tinkoff.homework_2.domain.model.StreamModel
import kz.tinkoff.homework_2.domain.model.TopicsModel
import kz.tinkoff.homework_2.domain.repository.StreamRepository

class RepoStreamImpl @Inject constructor(
    private val dataSource: StreamRemoteDataSource,
    private val streamMapper: StreamMapper,
    private val topicMapper: TopicMapper,
) : StreamRepository {

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
