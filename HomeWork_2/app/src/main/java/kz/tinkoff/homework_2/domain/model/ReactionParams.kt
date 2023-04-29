package kz.tinkoff.homework_2.domain.model

data class ReactionParams(
    val emojiName: String,
    val emojiCode: String? = null,
    val reactionType: String? = null,
)

