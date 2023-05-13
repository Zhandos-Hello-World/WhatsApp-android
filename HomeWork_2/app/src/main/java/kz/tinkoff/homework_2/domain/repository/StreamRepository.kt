package kz.tinkoff.homework_2.domain.repository

import kz.tinkoff.homework_2.domain.model.CreateStreamParams
import kz.tinkoff.homework_2.domain.model.StreamModel
import kz.tinkoff.homework_2.domain.model.SubscribedStreamModel
import kz.tinkoff.homework_2.domain.model.TopicsModel

interface StreamRepository {

    suspend fun getAllStreams(): List<StreamModel>

    suspend fun getSubscribedStreams(): List<SubscribedStreamModel>

    suspend fun findChannels(name: String): List<StreamModel>

    suspend fun createStream(params: CreateStreamParams): Boolean

    suspend fun findTopics(id: Int): TopicsModel
}
