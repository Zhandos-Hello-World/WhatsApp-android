package kz.tinkoff.homework_2.domain.repository

import kotlinx.coroutines.flow.Flow
import kz.tinkoff.homework_2.domain.model.MessageModel
import kz.tinkoff.homework_2.domain.model.MessageStreamParams
import kz.tinkoff.homework_2.domain.model.ReactionParams

interface MessageRepository {

    suspend fun getAllMessage(
        streamId: Int,
        stream: String,
        topic: String,
        numBefore: Int = 1000,
        numAfter: Int = 1000,
    ): List<MessageModel>

    fun getAllMessageLocally(stream: String, topic: String): Flow<List<MessageModel>>

    suspend fun sendMessage(params: MessageStreamParams)

    suspend fun addReaction(messageId: Int, params: ReactionParams)

    suspend fun deleteReaction(messageId: Int, params: ReactionParams)

    suspend fun saveDataLocally(data: List<MessageModel>)

    suspend fun updateMessage(messageId: Int, emoji: String)
}
