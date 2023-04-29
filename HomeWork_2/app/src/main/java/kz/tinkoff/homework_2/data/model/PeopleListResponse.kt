package kz.tinkoff.homework_2.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PeopleListResponse(
    @SerialName("members")
    val listResponse: List<PersonResponse>,
) {

    @Serializable
    data class PersonResponse(
        @SerialName("user_id")
        val id: Int,
        @SerialName("full_name")
        val fullName: String,
        @SerialName("email")
        val email: String,
        @SerialName("is_admin")
        val isAdmin: Boolean,
        @SerialName("is_guest")
        val isGuest: Boolean,
        @SerialName("is_owner")
        val isOwner: Boolean,
        @SerialName("is_active")
        val isOnline: Boolean,
        @SerialName("avatar_url")
        val avatarUrl: String?,
        @SerialName("timezone")
        val timezone: String,
        @SerialName("delivery_email")
        val deliveryEmail: String?,
    )
}
