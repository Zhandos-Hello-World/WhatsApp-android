package kz.tinkoff.homework_2.data.enitiy

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class MessageEntity(
    @PrimaryKey
    val id: Int,
    @ColumnInfo("sender_id")
    val senderId: Long,
    @ColumnInfo("recipient_id")
    val recipientId: Int,
    @ColumnInfo("timestamp")
    val timestamp: Long,
    @ColumnInfo("subject")
    val topic: String,
    @ColumnInfo("is_me_message")
    val isMeMessage: Boolean,
    @ColumnInfo("sender_full_name")
    val senderFullName: String,
    @ColumnInfo("sender_email")
    val senderEmail: String,
    @ColumnInfo("sender_realm_str")
    val senderRealmStr: String,
    @ColumnInfo("display_recipient")
    val displayRecipient: String? = null,
    @ColumnInfo("type")
    val type: String,
    @ColumnInfo("content")
    val content: String,
    @ColumnInfo("stream_id")
    val streamId: Int? = null,
    @ColumnInfo("avatar_url")
    val avatarUrl: String?,
    @ColumnInfo("reactions")
    val reactions: ReactionListEntity,
)
