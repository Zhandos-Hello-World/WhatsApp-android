package kz.tinkoff.homework_2.domain.datasource

import kotlinx.coroutines.flow.Flow
import kz.tinkoff.homework_2.domain.model.MessageModel

interface MessageLocalDataSource {
    fun getAllMessage(stream: String, topic: String): Flow<List<MessageModel>>

    fun getMessageByStreamId(streamId: Int): Flow<List<MessageModel>>

    suspend fun addMessage(messageModel: MessageModel)

    suspend fun updateMessage(messageId: Int, emoji: String)
}
