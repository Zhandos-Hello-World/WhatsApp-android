package kz.tinkoff.homework_2.data.model

data class ChannelListResponse(
    val list: List<ChannelResponse?>?
) {
    data class ChannelResponse(
        val id: Int?,
        val name: String?,
        val testingMessageCount: Int?,
        val brushMessageCount: Int?,
    )
}