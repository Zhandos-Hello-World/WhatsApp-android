package kz.tinkoff.homework_2.data.repository

import kz.tinkoff.homework_2.data.mappers.ChannelMapper
import kz.tinkoff.homework_2.domain.datasource.ChannelRemoteDataSource
import kz.tinkoff.homework_2.domain.model.ChannelModel
import kz.tinkoff.homework_2.domain.repository.ChannelRepository

class DefaultChannelRepository(
    private val dataSource: ChannelRemoteDataSource,
    private val mapper: ChannelMapper,
) : ChannelRepository {

    override suspend fun getAllChannels(): List<ChannelModel> {
        return mapper.toListChannel(dataSource.getAllChannels())
    }

    override suspend fun findChannels(name: String): List<ChannelModel> {
        return mapper.toListChannel(dataSource.findChannels(name))
    }
}