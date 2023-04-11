package kz.tinkoff.homework_2.domain.model

data class StreamModel(
    val id: Int,
    val name: String,
    var topics: List<TopicModel>,
) {

    data class TopicModel(
        val id: Int,
        val name: String,
    )
}