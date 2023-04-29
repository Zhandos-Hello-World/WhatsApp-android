package kz.tinkoff.homework_2.data.network

import kz.tinkoff.homework_2.data.model.StreamListResponse
import kz.tinkoff.homework_2.data.model.TopicListResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface StreamApiService {

    @GET("/api/v1/streams")
    suspend fun getAllStreams(): StreamListResponse

    @GET("/api/v1/users/me/{id}/topics")
    suspend fun getTopicsById(@Path("id") id: Int): TopicListResponse

    suspend fun findStreams(name: String): StreamListResponse
}