package kz.tinkoff.homework_2.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue

@Parcelize
data class MessageListResponse(
    @SerializedName("messages")
    val messages: List<MessageResponse>,
) : Parcelable {

    @Parcelize
    data class MessageResponse(
        val id: Int,
        @SerializedName("sender_id")
        val senderId: Long,
        @SerializedName("recipient_id")
        val recipientId: Int,
        val timestamp: Long,
        @SerializedName("subject")
        val topic: String,
        @SerializedName("is_me_message")
        val isMeMessage: Boolean,
        val reactions: List<ReactionResponse>,
        @SerializedName("sender_full_name")
        val senderFullName: String,
        @SerializedName("sender_email")
        val senderEmail: String,
        @SerializedName("sender_realm_str")
        val senderRealmStr: String,
        @SerializedName("display_recipient")
        val displayRecipient: @RawValue Any,
        val type: String,
        val content: String,

        @SerializedName("stream_id")
        val streamId: String?,
        @SerializedName("avatar_url")
        val avatarUrl: String,
        ) : Parcelable



    @Parcelize
    data class ReactionResponse(
        @SerializedName("emoji_name")
        val emojiName: String,
        @SerializedName("emoji_code")
        val emojiCode: String,
        @SerializedName("reaction_type")
        val reactionType: String,
        val user: UserResponse,
    ) : Parcelable

    @Parcelize
    data class UserResponse(
        val id: Int,
        @SerializedName("full_name") val fullName: String,
        val email: String,
    ) : Parcelable
}
