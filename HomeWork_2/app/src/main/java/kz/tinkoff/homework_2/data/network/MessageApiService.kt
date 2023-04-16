package kz.tinkoff.homework_2.data.network

import kz.tinkoff.homework_2.data.model.BaseResponse
import kz.tinkoff.homework_2.data.model.MessageListResponse
import retrofit2.http.FieldMap
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.HTTP
import retrofit2.http.POST
import retrofit2.http.Path

interface MessageApiService {

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