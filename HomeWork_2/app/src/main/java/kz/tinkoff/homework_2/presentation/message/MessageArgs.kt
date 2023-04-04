package kz.tinkoff.homework_2.presentation.message

data class MessageArgs(
    val streamId: Int,
    val stream: String,
    val topic: String
)