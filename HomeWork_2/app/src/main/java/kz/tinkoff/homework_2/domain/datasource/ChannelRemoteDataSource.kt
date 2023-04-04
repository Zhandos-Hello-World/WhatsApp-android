package kz.tinkoff.homework_2.domain.datasource

import kz.tinkoff.homework_2.data.model.StreamListResponse
import kz.tinkoff.homework_2.data.model.TopicListResponse

interface ChannelRemoteDataSource {

    suspend fun getAllChannels(): StreamListResponse

    suspend fun findChannels(name: String): StreamListResponse

    suspend fun findTopics(id: Int): TopicListResponse
}