package kz.tinkoff.homework_2.domain.repository

import kz.tinkoff.homework_2.domain.model.EditMessageParams
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

    suspend fun getAllMessageLocally(stream: String, topic: String): List<MessageModel>

    suspend fun sendMessage(params: MessageStreamParams): Int

    suspend fun addReaction(messageId: Int, params: ReactionParams)

    suspend fun deleteReaction(messageId: Int, params: ReactionParams)

    suspend fun saveDataLocally(data: List<MessageModel>)

    suspend fun deleteMessage(messageId: Int): Boolean

    suspend fun deleteMessageLocally(id: Int)

    suspend fun changeMessage(messageId: Int, params: EditMessageParams): Boolean

    suspend fun forwardMessage(messageId: Int, params: EditMessageParams): Boolean
}
