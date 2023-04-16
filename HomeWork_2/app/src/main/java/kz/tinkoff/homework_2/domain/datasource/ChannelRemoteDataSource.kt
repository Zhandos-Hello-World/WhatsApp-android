package kz.tinkoff.homework_2.domain.datasource

import kz.tinkoff.homework_2.data.model.StreamListResponse
import kz.tinkoff.homework_2.data.model.TopicListResponse

interface ChannelRemoteDataSource {

    suspend fun getAllStreams(): StreamListResponse

    suspend fun findStreams(name: String): StreamListResponse

    suspend fun findTopics(id: Int): TopicListResponse
}