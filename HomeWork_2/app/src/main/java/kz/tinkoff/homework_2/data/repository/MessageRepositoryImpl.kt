package kz.tinkoff.homework_2.data.repository

import javax.inject.Inject
import kz.tinkoff.homework_2.data.mappers.EditMessageDomainToDataMapper
import kz.tinkoff.homework_2.data.mappers.MessageParamsDomainToDataMapper
import kz.tinkoff.homework_2.data.mappers.MessageDataToDomainMapper
import kz.tinkoff.homework_2.data.mappers.ReactionParamsDomainToDataMapper
import kz.tinkoff.homework_2.domain.datasource.MessageLocalDataSource
import kz.tinkoff.homework_2.domain.datasource.MessageRemoteDataSource
import kz.tinkoff.homework_2.domain.model.EditMessageParams
import kz.tinkoff.homework_2.domain.model.MessageModel
import kz.tinkoff.homework_2.domain.model.MessageStreamParams
import kz.tinkoff.homework_2.domain.model.ReactionParams
import kz.tinkoff.homework_2.domain.repository.MessageRepository

class MessageRepositoryImpl @Inject constructor(
    private val remoteDataSource: MessageRemoteDataSource,
    private val localDataSource: MessageLocalDataSource,
    private val mapper: MessageDataToDomainMapper,
    private val dtoMessageMapper: MessageParamsDomainToDataMapper,
    private val dtoReactionMapper: ReactionParamsDomainToDataMapper,
    private val editMessageDomainToDataMapper: EditMessageDomainToDataMapper,
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

    override suspend fun deleteMessage(messageId: Int): Boolean {
        return remoteDataSource.deleteMessage(messageId).isSuccess()
    }

    override suspend fun changeMessage(messageId: Int, params: EditMessageParams): Boolean {
        return remoteDataSource.changeMessage(messageId, editMessageDomainToDataMapper.map(params, true))
            .isSuccess()
    }

    override suspend fun forwardMessage(messageId: Int, params: EditMessageParams): Boolean {
        return remoteDataSource.changeMessage(
            messageId, editMessageDomainToDataMapper.map(
                from = params,
                changeContent = false
            )
        )
            .isSuccess()
    }

    override suspend fun deleteMessageLocally(id: Int) {
        return localDataSource.deleteMessage(id)
    }

    override suspend fun getAllMessageLocally(stream: String, topic: String): List<MessageModel> {
        return localDataSource.getAllMessage(stream, topic)
    }

    override suspend fun sendMessage(params: MessageStreamParams): Int {
        return remoteDataSource.setMessageSend(dtoMessageMapper.map(from = params)).id ?: -1
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
