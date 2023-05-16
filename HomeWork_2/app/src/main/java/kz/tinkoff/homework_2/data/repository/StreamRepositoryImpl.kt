package kz.tinkoff.homework_2.data.repository

import javax.inject.Inject
import kz.tinkoff.homework_2.data.mappers.CreateStreamDomainToDataMapper
import kz.tinkoff.homework_2.data.mappers.StreamDataToDomainMapper
import kz.tinkoff.homework_2.data.mappers.SubscribedStreamDataToDomainMapper
import kz.tinkoff.homework_2.data.mappers.TopicDataToDomainMapper
import kz.tinkoff.homework_2.domain.datasource.StreamRemoteDataSource
import kz.tinkoff.homework_2.domain.model.CreateStreamParams
import kz.tinkoff.homework_2.domain.model.StreamModel
import kz.tinkoff.homework_2.domain.model.SubscribedStreamModel
import kz.tinkoff.homework_2.domain.model.TopicsModel
import kz.tinkoff.homework_2.domain.repository.StreamRepository

class StreamRepositoryImpl @Inject constructor(
    private val dataSource: StreamRemoteDataSource,
    private val streamDataToDomainMapper: StreamDataToDomainMapper,
    private val subscribedStreamDataToDomainMapper: SubscribedStreamDataToDomainMapper,
    private val createStreamDomainToDataMapper: CreateStreamDomainToDataMapper,
    private val topicDataToDomainMapper: TopicDataToDomainMapper,
) : StreamRepository {

    override suspend fun getAllStreams(): List<StreamModel> {
        return streamDataToDomainMapper.toListOfStream(dataSource.getAllStreams())
    }

    override suspend fun getSubscribedStreams(): List<SubscribedStreamModel> {
        return dataSource.getSubscribedStreams().subscriptions.map { subscribedStreamDataToDomainMapper.map(it) }
    }

    override suspend fun findChannels(name: String): List<StreamModel> {
        return streamDataToDomainMapper.toListOfStream(dataSource.findStreams(name))
    }

    override suspend fun createStream(params: CreateStreamParams): Boolean {
        val filter = createStreamDomainToDataMapper.map(params)
        return dataSource.createStream(filter).isSuccess()
    }

    override suspend fun findTopics(id: Int): TopicsModel {
        return topicDataToDomainMapper.map(dataSource.findTopics(id))
    }
}
