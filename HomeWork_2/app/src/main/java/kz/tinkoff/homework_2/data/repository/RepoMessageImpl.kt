package kz.tinkoff.homework_2.data.repository

import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kz.tinkoff.homework_2.data.mappers.MessageDtoMapper
import kz.tinkoff.homework_2.data.mappers.MessageMapper
import kz.tinkoff.homework_2.data.mappers.ReactionDtoMapper
import kz.tinkoff.homework_2.domain.datasource.MessageLocalDataSource
import kz.tinkoff.homework_2.domain.datasource.MessageRemoteDataSource
import kz.tinkoff.homework_2.domain.model.MessageModel
import kz.tinkoff.homework_2.domain.model.MessageStreamParams
import kz.tinkoff.homework_2.domain.model.ReactionParams
import kz.tinkoff.homework_2.domain.repository.MessageRepository

class RepoMessageImpl @Inject constructor(
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
        numBefore: Int ,
        numAfter: Int ,
    ): List<MessageModel> {
        val response = remoteDataSource.getAllMessage(
            stream)
        val mappedResponse = mapper.map(response)
        val filter = mappedResponse.filter {
            it.topic == topic
        }
        filter.forEach { localDataSource.addMessage(it) }
        return filter
    }

    override fun getAllMessageLocally(streamId: Int): Flow<List<MessageModel>> {
        return localDataSource.getMessageByStreamId(streamId)
    }

    override suspend fun sendMessage(params: MessageStreamParams) {
        remoteDataSource.setMessageSend(dtoMessageMapper.map(from = params))
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
