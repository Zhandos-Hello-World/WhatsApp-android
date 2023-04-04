package kz.tinkoff.homework_2.data.datasource

import kz.tinkoff.homework_2.data.model.StreamListResponse
import kz.tinkoff.homework_2.data.model.TopicListResponse
import kz.tinkoff.homework_2.data.network.ApiService
import kz.tinkoff.homework_2.domain.datasource.ChannelRemoteDataSource

class DefaultChannelsNetworkDataSource(
    private val apiService: ApiService,
) : ChannelRemoteDataSource {

    override suspend fun getAllChannels(): StreamListResponse {
        return apiService.getAllStreams()
    }

    override suspend fun findChannels(name: String): StreamListResponse {
        return apiService.findChannels(name)
    }

    override suspend fun findTopics(id: Int): TopicListResponse {
        return apiService.getTopicsById(id)
    }
}