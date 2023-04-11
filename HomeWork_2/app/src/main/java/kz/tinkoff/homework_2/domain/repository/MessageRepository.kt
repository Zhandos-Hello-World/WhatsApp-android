package kz.tinkoff.homework_2.domain.repository

import kz.tinkoff.homework_2.domain.model.MessageModel
import kz.tinkoff.homework_2.domain.model.MessageStreamParams
import kz.tinkoff.homework_2.domain.model.ReactionParams

interface MessageRepository {

    suspend fun getAllMessage(
        streamId: Int,
        stream: String,
        topic: String
    ): List<MessageModel>


    suspend fun sendMessage(params: MessageStreamParams): Boolean

    suspend fun addReaction(messageId: Int, params: ReactionParams): Boolean

    suspend fun deleteReaction(messageId: Int, params: ReactionParams): Boolean
}