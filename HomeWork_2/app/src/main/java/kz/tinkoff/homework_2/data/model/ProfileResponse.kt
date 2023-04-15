package kz.tinkoff.homework_2.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

// Зачем Parcelable?
@Parcelize
data class ProfileResponse(
    @SerializedName("full_name")
    val fullName: String,
    // SerializedName лучше везде указывать
    val email: String,
    @SerializedName("user_id")
    val userId: Int,
    @SerializedName("avatar_version")
    val avatarVersion: Int,
    @SerializedName("is_admin")
    val isAdmin: Boolean,
    @SerializedName("is_owner")
    val isOwner: Boolean,
    @SerializedName("is_guest")
    val isGuest: Boolean,
    val timezone: String,
    @SerializedName("is_active")
    val isActive: Boolean,
    @SerializedName("avatar_url")
    val avatarUrl: String,
    @SerializedName("date_joined")
    val dateJoined: String,
    @SerializedName("delivery_email")
    val deliveredEmail: String
) : Parcelable
