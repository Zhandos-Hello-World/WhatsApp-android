package kz.tinkoff.homework_2.data.datasource

import javax.inject.Inject
import kz.tinkoff.homework_2.data.model.StreamListResponse
import kz.tinkoff.homework_2.data.model.TopicListResponse
import kz.tinkoff.homework_2.data.network.StreamApiService
import kz.tinkoff.homework_2.domain.datasource.StreamRemoteDataSource

class DefaultStreamNetworkDataSource @Inject constructor(
    private val apiService: StreamApiService,
) : StreamRemoteDataSource {

    override suspend fun getAllStreams(): StreamListResponse {
        return apiService.getAllStreams()
    }

    override suspend fun findStreams(name: String): StreamListResponse {
        return apiService.findStreams(name)
    }

    override suspend fun findTopics(id: Int): TopicListResponse {
        return apiService.getTopicsById(id)
    }
}
