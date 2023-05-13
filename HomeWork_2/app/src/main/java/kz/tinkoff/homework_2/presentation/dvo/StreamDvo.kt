package kz.tinkoff.homework_2.presentation.dvo

import androidx.annotation.ColorRes

data class StreamDvo(
    val id: Int,
    val name: String,
    var topicsDvo: List<TopicDvo>,
    var expanded: Boolean = false,
) {

    data class TopicDvo(
        val id: Int,
        val name: String,
        @ColorRes val color: Int,
    )
}
