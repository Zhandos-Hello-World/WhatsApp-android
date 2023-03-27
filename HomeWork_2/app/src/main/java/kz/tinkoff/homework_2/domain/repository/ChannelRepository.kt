package kz.tinkoff.homework_2.domain.repository

import kz.tinkoff.homework_2.domain.model.ChannelModel

interface ChannelRepository {

    suspend fun getAllChannels(): List<ChannelModel>

    suspend fun findChannels(name: String): List<ChannelModel>
}