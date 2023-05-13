package kz.tinkoff.homework_2.data.network

import kz.tinkoff.homework_2.data.model.BaseResponse
import kz.tinkoff.homework_2.data.model.StreamListResponse
import kz.tinkoff.homework_2.data.model.SubscribedStreamListResponse
import kz.tinkoff.homework_2.data.model.TopicListResponse
import retrofit2.http.FieldMap
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

@JvmSuppressWildcards
interface StreamApiService {

    @GET("/api/v1/streams")
    suspend fun getAllStreams(): StreamListResponse

    @GET("/api/v1/users/me/subscriptions")
    suspend fun getSubscribedStream(): SubscribedStreamListResponse

    @GET("/api/v1/users/me/{id}/topics")
    suspend fun getTopicsById(@Path("id") id: Int): TopicListResponse

    @POST("/api/v1/users/me/subscriptions")
    @FormUrlEncoded
    suspend fun createStream(@FieldMap request: HashMap<String, String>): BaseResponse

    suspend fun findStreams(name: String): StreamListResponse
}
