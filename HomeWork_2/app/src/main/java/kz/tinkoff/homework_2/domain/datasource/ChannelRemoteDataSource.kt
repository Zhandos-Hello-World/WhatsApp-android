package kz.tinkoff.homework_2.domain.datasource

import kz.tinkoff.homework_2.domain.model.ChannelModel

interface ChannelRemoteDataSource {

    suspend fun getAllChannels(): List<ChannelModel>

    suspend fun findChannels(name: String): List<ChannelModel>

}