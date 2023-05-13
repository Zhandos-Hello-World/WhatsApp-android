package kz.tinkoff.homework_2.data.repository

import javax.inject.Inject
import kz.tinkoff.homework_2.data.mappers.MessageDtoMapper
import kz.tinkoff.homework_2.data.mappers.MessageMapper
import kz.tinkoff.homework_2.data.mappers.ReactionDtoMapper
import kz.tinkoff.homework_2.domain.datasource.MessageLocalDataSource
import kz.tinkoff.homework_2.domain.datasource.MessageRemoteDataSource
import kz.tinkoff.homework_2.domain.model.MessageModel
import kz.tinkoff.homework_2.domain.model.MessageStreamParams
import kz.tinkoff.homework_2.domain.model.ReactionParams
import kz.tinkoff.homework_2.domain.repository.MessageRepository

class MessageRepositoryImpl @Inject constructor(
    private val remoteDataSource: MessageRemoteDataSource,
    private val localDataSource: MessageLocalDataSource,
    private val mapper: MessageMapper,
    private val dtoMessageMapper: MessageDtoMapper,
    private val dtoReactionMapper: ReactionDtoMapper,
) : MessageRepository {

    override suspend fun getAllMessage(
        streamId: Int,
        stream: String,
        topic: String,
        numBefore: Int,
        numAfter: Int,
    ): List<MessageModel> {
        val response = remoteDataSource.getAllMessage(stream)
        val mappedResponse = mapper.map(response)
        return mappedResponse.filter { it.topic == topic }
    }

    override suspend fun saveDataLocally(data: List<MessageModel>) {
        data.forEach { localDataSource.addMessage(it) }
    }

    override suspend fun getAllMessageLocally(stream: String, topic: String): List<MessageModel> {
        return localDataSource.getAllMessage(stream, topic)
    }

    override suspend fun sendMessage(params: MessageStreamParams): Boolean {
        return remoteDataSource.setMessageSend(dtoMessageMapper.map(from = params)).isSuccess()
    }

    override suspend fun addReaction(messageId: Int, params: ReactionParams) {
        remoteDataSource.addReaction(
            messageId = messageId,
            request = dtoReactionMapper.map(params)
        )
    }

    override suspend fun deleteReaction(messageId: Int, params: ReactionParams) {
        remoteDataSource.deleteReaction(
            messageId = messageId,
            request = dtoReactionMapper.map(params)
        )
    }
}
