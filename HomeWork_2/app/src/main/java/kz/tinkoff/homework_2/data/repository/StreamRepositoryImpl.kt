package kz.tinkoff.homework_2.data.repository

import javax.inject.Inject
import kz.tinkoff.homework_2.data.mappers.CreateStreamDtoMapper
import kz.tinkoff.homework_2.data.mappers.StreamMapper
import kz.tinkoff.homework_2.data.mappers.SubscribedStreamMapper
import kz.tinkoff.homework_2.data.mappers.TopicMapper
import kz.tinkoff.homework_2.domain.datasource.StreamRemoteDataSource
import kz.tinkoff.homework_2.domain.model.CreateStreamParams
import kz.tinkoff.homework_2.domain.model.StreamModel
import kz.tinkoff.homework_2.domain.model.SubscribedStreamModel
import kz.tinkoff.homework_2.domain.model.TopicsModel
import kz.tinkoff.homework_2.domain.repository.StreamRepository

class StreamRepositoryImpl @Inject constructor(
    private val dataSource: StreamRemoteDataSource,
    private val streamMapper: StreamMapper,
    private val subscribedStreamMapper: SubscribedStreamMapper,
    private val createStreamDtoMapper: CreateStreamDtoMapper,
    private val topicMapper: TopicMapper,
) : StreamRepository {

    override suspend fun getAllStreams(): List<StreamModel> {
        return streamMapper.toListOfStream(dataSource.getAllStreams())
    }

    override suspend fun getSubscribedStreams(): List<SubscribedStreamModel> {
        return dataSource.getSubscribedStreams().subscriptions.map { subscribedStreamMapper.map(it) }
    }

    override suspend fun findChannels(name: String): List<StreamModel> {
        return streamMapper.toListOfStream(dataSource.findStreams(name))
    }

    override suspend fun createStream(params: CreateStreamParams): Boolean {
        val filter = createStreamDtoMapper.map(params)
        return dataSource.createStream(filter).isSuccess()
    }

    override suspend fun findTopics(id: Int): TopicsModel {
        return topicMapper.map(dataSource.findTopics(id))
    }
}
