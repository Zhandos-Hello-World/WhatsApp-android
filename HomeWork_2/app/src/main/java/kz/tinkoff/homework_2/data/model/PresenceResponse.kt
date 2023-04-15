package kz.tinkoff.homework_2.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

// Зачем Parcelable?
@Parcelize
data class PresenceResponse(
    @SerializedName("presence")
    val presence: Presence,
) : Parcelable {

    @Parcelize
    data class Presence(
        @SerializedName("aggregated")
        val aggregated: Aggregated,
    ) : Parcelable

    @Parcelize
    data class Aggregated(
        val status: String,
        val timestamp: Long,
    ) : Parcelable
}
