package kz.tinkoff.homework_2.presentation.delegates.channels

data class ChannelModel(
    val id: Int,
    val name: String,
    val testingMessageCount: Int,
    val brushMessageCount: Int,
    var expanded: Boolean = false
)