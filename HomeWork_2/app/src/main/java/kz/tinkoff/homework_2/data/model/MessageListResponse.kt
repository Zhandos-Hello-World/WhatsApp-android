package kz.tinkoff.homework_2.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement

@Serializable
data class MessageListResponse(
    @SerialName("messages")
    val messages: List<MessageResponse>,
) {

    @Serializable
    data class MessageResponse(
        @SerialName("id")
        val id: Int,
        @SerialName("sender_id")
        val senderId: Long,
        @SerialName("recipient_id")
        val recipientId: Int,
        val timestamp: Long,
        @SerialName("subject")
        val topic: String,
        @SerialName("is_me_message")
        val isMeMessage: Boolean,
        @SerialName("reactions")
        val reactions: List<ReactionResponse>,
        @SerialName("sender_full_name")
        val senderFullName: String,
        @SerialName("sender_email")
        val senderEmail: String,
        @SerialName("sender_realm_str")
        val senderRealmStr: String,
        @SerialName("display_recipient")
        val displayRecipient: JsonElement? = null,
        @SerialName("type")
        val type: String,
        @SerialName("content")
        val content: String,
        @SerialName("stream_id")
        val streamId: Int? = null,
        @SerialName("avatar_url")
        val avatarUrl: String?,
    )

    @Serializable
    data class ReactionResponse(
        @SerialName("emoji_name")
        val emojiName: String,
        @SerialName("emoji_code")
        val emojiCode: String,
        @SerialName("reaction_type")
        val reactionType: String,
        @SerialName("user")
        val user: UserResponse,
    )

    @Serializable
    data class UserResponse(
        @SerialName("id")
        val id: Int,
        @SerialName("full_name")
        val fullName: String,
        @SerialName("email")
        val email: String,
        @SerialName("is_mirror_dummy")
        val isMirrorDummy: Boolean = false,
    )
}
