package kz.tinkoff.homework_2.domain.model

data class MessageModel(
    val id: Int,
    val senderId: Long,
    val recipientId: Int,
    val timestamp: Long,
    val topic: String,
    val isMeMessage: Boolean,
    val reactions: List<ReactionModel>,
    val senderFullName: String,
    val senderEmail: String,
    val senderRealmStr: String,
    val content: String,
    val displayRecipient: String,
    val type: String,
    val streamId: String,
    val avatarUrl: String,
) {

    data class ReactionModel(
        val emojiName: String,
        val emojiCode: String,
        val reactionType: String,
        val user: UserModel,
    )

    data class UserModel(
        val id: Int,
        val fullName: String,
        val email: String,
    )
}