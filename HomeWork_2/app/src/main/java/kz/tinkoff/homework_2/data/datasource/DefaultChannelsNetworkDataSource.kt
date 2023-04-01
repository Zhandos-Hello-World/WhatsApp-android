package kz.tinkoff.homework_2.data.datasource

import kz.tinkoff.homework_2.data.model.ChannelListResponse
import kz.tinkoff.homework_2.data.network.ApiService
import kz.tinkoff.homework_2.domain.datasource.ChannelRemoteDataSource

class DefaultChannelsNetworkDataSource(
    private val apiService: ApiService,
) : ChannelRemoteDataSource {

    override suspend fun getAllChannels(): ChannelListResponse {
        return apiService.getAllChannels()
    }

    override suspend fun findChannels(name: String): ChannelListResponse {
        return apiService.findChannels(name)
    }
}