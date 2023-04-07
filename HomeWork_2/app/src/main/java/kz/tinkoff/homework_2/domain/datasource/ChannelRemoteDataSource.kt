package kz.tinkoff.homework_2.domain.datasource

import kz.tinkoff.homework_2.data.model.ChannelListResponse

interface ChannelRemoteDataSource {

    suspend fun getAllChannels(): ChannelListResponse

    suspend fun findChannels(name: String): ChannelListResponse

}