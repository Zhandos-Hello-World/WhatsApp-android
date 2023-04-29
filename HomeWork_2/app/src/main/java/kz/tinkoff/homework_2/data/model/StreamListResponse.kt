package kz.tinkoff.homework_2.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class StreamListResponse(
    @SerialName("streams")
    val streams: List<StreamResponse>,
) {

    @Serializable
    data class StreamResponse(
        @SerialName("stream_id")
        val id: Int,
        @SerialName("name")
        val name: String,
        @SerialName("date_created")
        val dateCreated: Long,
        @SerialName("description")
        val description: String,
        @SerialName("first_message_id")
        val firstMessageId: String,
        @SerialName("stream_post_policy")
        val streamPostPolicy: Int,
    )
}
