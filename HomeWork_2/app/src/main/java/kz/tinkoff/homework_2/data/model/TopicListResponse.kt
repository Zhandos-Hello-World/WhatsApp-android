package kz.tinkoff.homework_2.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TopicListResponse(
    @SerialName("topics")
    val topics: List<TopicResponse>,
) {

    @Serializable
    data class TopicResponse(
        @SerialName("max_id")
        val id: Int,
        @SerialName("name")
        val name: String,
    )
}
