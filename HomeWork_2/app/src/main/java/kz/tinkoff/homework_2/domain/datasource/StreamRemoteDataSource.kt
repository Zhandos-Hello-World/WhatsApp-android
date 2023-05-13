package kz.tinkoff.homework_2.domain.datasource

import kz.tinkoff.homework_2.data.model.BaseResponse
import kz.tinkoff.homework_2.data.model.StreamListResponse
import kz.tinkoff.homework_2.data.model.SubscribedStreamListResponse
import kz.tinkoff.homework_2.data.model.TopicListResponse

interface StreamRemoteDataSource {

    suspend fun getAllStreams(): StreamListResponse

    suspend fun getSubscribedStreams(): SubscribedStreamListResponse

    suspend fun findStreams(name: String): StreamListResponse

    suspend fun createStream(request: HashMap<String, String>): BaseResponse

    suspend fun findTopics(id: Int): TopicListResponse
}
