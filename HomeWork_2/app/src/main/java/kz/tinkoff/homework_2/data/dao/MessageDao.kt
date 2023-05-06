package kz.tinkoff.homework_2.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow
import kz.tinkoff.homework_2.data.enitiy.MessageEntity
import kz.tinkoff.homework_2.domain.model.MessageModel

@Dao
interface MessageDao {

    @Query("SELECT * FROM MessageEntity WHERE display_recipient == :stream AND subject == :topic")
    fun getAllMessages(stream: String, topic: String): Flow<List<MessageEntity>>

    @Query("SELECT * FROM MessageEntity WHERE id == :id")
    fun getMessageById(id: Int): Flow<MessageEntity>

    @Query("SELECT * FROM MessageEntity WHERE stream_id == :streamId")
    fun getMessageByStream(streamId: Int): Flow<List<MessageEntity>>

    @Insert(entity = MessageEntity::class, onConflict = OnConflictStrategy.REPLACE)
    suspend fun addMessage(message: MessageEntity)

    @Update(entity = MessageEntity::class)
    suspend fun updateMessage(message: MessageEntity)
}
