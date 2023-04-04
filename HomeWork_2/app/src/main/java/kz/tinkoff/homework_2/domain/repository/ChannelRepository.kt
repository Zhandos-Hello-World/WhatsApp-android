package kz.tinkoff.homework_2.domain.repository

import kz.tinkoff.homework_2.domain.model.StreamModel
import kz.tinkoff.homework_2.domain.model.TopicsModel

interface ChannelRepository {

    suspend fun getAllChannels(): List<StreamModel>

    suspend fun findChannels(name: String): List<StreamModel>

    suspend fun findTopics(id: Int): TopicsModel
}