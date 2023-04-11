package kz.tinkoff.homework_2.data.model

data class MessageRequest(
    val type: String,
    val to: Int,
    val content: String,
    val topic: String,
    val queueId: String? = null,
    val localId: String? = null
)