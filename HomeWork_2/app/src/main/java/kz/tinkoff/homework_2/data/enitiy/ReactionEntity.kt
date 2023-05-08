package kz.tinkoff.homework_2.data.enitiy

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ReactionEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Long? = null,
    @ColumnInfo("emoji_name")
    val emojiName: String,
    @ColumnInfo("emoji_code")
    val emojiCode: String,
    @ColumnInfo("reaction_type")
    val reactionType: String,
    @ColumnInfo("user")
    val user: UserEntity,
)
