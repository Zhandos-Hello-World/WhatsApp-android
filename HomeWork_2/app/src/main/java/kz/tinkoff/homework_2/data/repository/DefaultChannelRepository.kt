package kz.tinkoff.homework_2.data.repository

import kz.tinkoff.homework_2.domain.datasource.ChannelRemoteDataSource
import kz.tinkoff.homework_2.domain.model.ChannelModel
import kz.tinkoff.homework_2.domain.repository.ChannelRepository

class DefaultChannelRepository(private val dataSource: ChannelRemoteDataSource) :
    ChannelRepository {

    override suspend fun getAllChannels(): List<ChannelModel> {
        return dataSource.getAllChannels()
    }

    override suspend fun findChannels(name: String): List<ChannelModel> {
        return dataSource.findChannels(name)
    }
}