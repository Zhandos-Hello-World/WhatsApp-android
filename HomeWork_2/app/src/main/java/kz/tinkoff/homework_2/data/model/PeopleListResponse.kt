package kz.tinkoff.homework_2.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

// Зачем Parcelable?
@Parcelize
data class PeopleListResponse(
    @SerializedName("members")
    val listResponse: List<PersonResponse>
): Parcelable {
    @Parcelize
    data class PersonResponse(
        @SerializedName("user_id")
        val id: Int,
        @SerializedName("full_name")
        val fullName: String,
        val email: String,
        @SerializedName("is_admin")
        val isAdmin: Boolean,
        @SerializedName("is_guest")
        val isGuest: Boolean,
        @SerializedName("is_owner")
        val isOwner: Boolean,
        @SerializedName("is_active")
        val isOnline: Boolean,
        @SerializedName("avatar_url")
        val avatarUrl: String,
        val timezone: String,
        @SerializedName("delivery_email")
        val deliveryEmail: String?
    ) : Parcelable
}
