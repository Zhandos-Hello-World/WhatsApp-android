package kz.tinkoff.homework_2.domain.model

data class ChannelModel(
    val id: Int,
    val name: String,
    val topics: List<TopicModel>,
) {

    data class TopicModel(
        val id: Int,
        val name: String,
        val count: Int,
    )
}