package kz.tinkoff.homework_2.data.network

import kz.tinkoff.homework_2.data.model.BaseResponse
import kz.tinkoff.homework_2.data.model.MessageListResponse
import kz.tinkoff.homework_2.data.model.PeopleListResponse
import kz.tinkoff.homework_2.data.model.PresenceResponse
import kz.tinkoff.homework_2.data.model.ProfileResponse
import kz.tinkoff.homework_2.data.model.StreamListResponse
import kz.tinkoff.homework_2.data.model.TopicListResponse
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.FieldMap
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.HTTP
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiService {

    @GET("/api/v1/streams")
    suspend fun getAllStreams(): StreamListResponse

    @GET("/api/v1/users/me/{id}/topics")
    suspend fun getTopicsById(@Path("id") id: Int): TopicListResponse

    suspend fun findChannels(name: String): StreamListResponse

    @GET("/api/v1/users")
    suspend fun getAllPeople(): PeopleListResponse

    suspend fun findPerson(name: String): PeopleListResponse

    @GET("/api/v1/users/me")
    suspend fun getProfile(): ProfileResponse

    @GET("/api/v1/users/{user_id_or_email}/presence")
    suspend fun getPresence(@Path("user_id_or_email") userIdOrEmail: String): PresenceResponse

    @GET("/api/v1/messages?num_before=1000&num_after=1000&anchor=newest")
    suspend fun getAllMessage(): MessageListResponse

    @POST("/api/v1/messages")
    @FormUrlEncoded
    @JvmSuppressWildcards
    suspend fun sendMessageStream(@FieldMap request: HashMap<String, Any?>): BaseResponse


    @POST("/api/v1/messages/{message_id}/reactions")
    @FormUrlEncoded
    @JvmSuppressWildcards
    suspend fun addReaction(@Path("message_id") messageId: Int, @FieldMap request: HashMap<String, String>): BaseResponse

    @HTTP(method = "DELETE", path = "/api/v1/messages/{message_id}/reactions", hasBody = true)
    @FormUrlEncoded
    @JvmSuppressWildcards
    suspend fun deleteReaction(@Path("message_id") messageId: Int, @FieldMap request: HashMap<String, String>): BaseResponse
}