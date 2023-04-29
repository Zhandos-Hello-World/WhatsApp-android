package kz.tinkoff.homework_2.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ProfileResponse(
    @SerialName("full_name")
    val fullName: String,
    @SerialName("email")
    val email: String,
    @SerialName("user_id")
    val userId: Int,
    @SerialName("avatar_version")
    val avatarVersion: Int,
    @SerialName("is_admin")
    val isAdmin: Boolean,
    @SerialName("is_owner")
    val isOwner: Boolean,
    @SerialName("is_guest")
    val isGuest: Boolean,
    @SerialName("timezone")
    val timezone: String,
    @SerialName("is_active")
    val isActive: Boolean,
    @SerialName("avatar_url")
    val avatarUrl: String?,
    @SerialName("date_joined")
    val dateJoined: String,
    @SerialName("delivery_email")
    val deliveredEmail: String,
)
