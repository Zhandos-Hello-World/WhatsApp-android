package kz.tinkoff.homework_2.data.model

data class ChannelListResponse(
    val list: List<ChannelResponse>,
) {
    data class ChannelResponse(
        val id: Int,
        val name: String,
        val topics: List<TopicResponse>,
    )

    data class TopicResponse(
        val id: Int,
        val name: String,
        val messageCount: Int,
    )
}