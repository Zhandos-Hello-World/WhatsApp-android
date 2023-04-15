package kz.tinkoff.homework_2.domain.model

data class ReactionParams(
    val emojiName: String,
    val emojiCode: String? = null,
    val reactionType: String? = null
)
// Везде в конце файлов нужно оставлять пустую строчку
// В Android Studio: Editor > General > Ensure line feed at file end on Save
