package kz.tinkoff.homework_2.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class StreamListResponse(
    val streams: List<StreamResponse>,
): Parcelable {

    @Parcelize
    data class StreamResponse(
        @SerializedName("stream_id")
        val id: Int,
        val name: String,
        @SerializedName("date_created")
        val dateCreated: Long,
        val description: String,
        @SerializedName("first_message_id")
        val firstMessageId: String,
        @SerializedName("stream_post_policy")
        val streamPostPolicy: Int,
    ) : Parcelable

}