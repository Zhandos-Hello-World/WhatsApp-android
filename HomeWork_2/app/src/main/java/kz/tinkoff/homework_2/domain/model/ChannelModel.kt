package kz.tinkoff.homework_2.domain.model

data class ChannelModel(
    val id: Int,
    val name: String,
    val testingMessageCount: Int,
    val brushMessageCount: Int,
)