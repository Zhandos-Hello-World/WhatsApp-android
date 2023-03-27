package kz.tinkoff.homework_2.data.datasource

import kz.tinkoff.homework_2.data.mappers.ChannelMapper
import kz.tinkoff.homework_2.data.network.ApiService
import kz.tinkoff.homework_2.domain.datasource.ChannelRemoteDataSource
import kz.tinkoff.homework_2.domain.model.ChannelModel

class DefaultChannelsNetworkDataSource(
    private val apiService: ApiService,
    private val mapper: ChannelMapper,
) : ChannelRemoteDataSource {

    override suspend fun getAllChannels(): List<ChannelModel> {
        return mapper.toListChannel(apiService.getAllChannels())
    }

    override suspend fun findChannels(name: String): List<ChannelModel> {
        return mapper.toListChannel(apiService.findChannels(name))
    }
}