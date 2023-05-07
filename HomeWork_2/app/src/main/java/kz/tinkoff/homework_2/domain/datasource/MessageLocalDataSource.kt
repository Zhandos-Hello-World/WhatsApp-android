package kz.tinkoff.homework_2.domain.datasource

import kotlinx.coroutines.flow.Flow
import kz.tinkoff.homework_2.domain.model.MessageModel

interface MessageLocalDataSource {
    suspend fun getAllMessage(stream: String, topic: String): List<MessageModel>

    fun getMessageByStreamId(streamId: Int): Flow<List<MessageModel>>

    suspend fun addMessage(messageModel: MessageModel)
}
