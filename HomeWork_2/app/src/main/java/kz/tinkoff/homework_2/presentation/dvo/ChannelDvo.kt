package kz.tinkoff.homework_2.presentation.dvo

data class ChannelDvo(
    val id: Int,
    val name: String,
    val topicsDvo: List<TopicDvo>,
    var expanded: Boolean = false
) {

    data class TopicDvo(
        val id: Int,
        val name: String,
        val count: Int
    )
}