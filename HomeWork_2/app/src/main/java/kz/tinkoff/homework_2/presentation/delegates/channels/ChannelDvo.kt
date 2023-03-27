package kz.tinkoff.homework_2.presentation.delegates.channels

data class ChannelDvo(
    val id: Int,
    val name: String,
    val testingMessageCount: Int,
    val brushMessageCount: Int,
    var expanded: Boolean = false
)