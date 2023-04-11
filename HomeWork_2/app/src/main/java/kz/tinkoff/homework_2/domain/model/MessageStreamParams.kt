package kz.tinkoff.homework_2.domain.model

data class MessageStreamParams(
    val type: String,
    val to: Int,
    val content: String,
    val topic: String,
    val queueId: String? = null,
    val localId: String? = null
) {

    companion object {
        const val STREAM = "stream"
    }
}