package kz.tinkoff.homework_2.data.network

import kz.tinkoff.homework_2.data.model.PeopleListResponse
import kz.tinkoff.homework_2.data.model.PresenceResponse
import kz.tinkoff.homework_2.data.model.ProfileResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface PeopleApiService {

    @GET("/api/v1/users")
    suspend fun getAllPeople(): PeopleListResponse

    @GET("/api/v1/users/me")
    suspend fun getProfile(): ProfileResponse

    @GET("/api/v1/users/{user_id_or_email}/presence")
    suspend fun getPresence(@Path("user_id_or_email") userIdOrEmail: String): PresenceResponse

}