package kz.tinkoff.homework_2.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import kz.tinkoff.homework_2.data.enitiy.MessageEntity

@Dao
interface MessageDao {

    @Query("SELECT * FROM MessageEntity WHERE display_recipient == :stream AND subject == :topic")
    suspend fun getAllMessages(stream: String, topic: String): List<MessageEntity>

    @Query("SELECT * FROM MessageEntity WHERE id == :id")
    fun getMessageById(id: Int): Flow<MessageEntity>

    @Query("SELECT * FROM MessageEntity WHERE stream_id == :streamId")
    fun getMessageByStream(streamId: Int): Flow<List<MessageEntity>>

    @Insert(entity = MessageEntity::class, onConflict = OnConflictStrategy.REPLACE)
    suspend fun addMessage(message: MessageEntity)

    @Query("DELETE FROM MessageEntity WHERE id = :id")
    suspend fun deleteMessage(id: Int)

}
