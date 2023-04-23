package kz.tinkoff.homework_2.data.repository

import javax.inject.Inject
import kz.tinkoff.homework_2.data.mappers.MessageDtoMapper
import kz.tinkoff.homework_2.data.mappers.MessageMapper
import kz.tinkoff.homework_2.data.mappers.ReactionDtoMapper
import kz.tinkoff.homework_2.domain.datasource.MessageRemoteDataSource
import kz.tinkoff.homework_2.domain.model.MessageModel
import kz.tinkoff.homework_2.domain.model.MessageStreamParams
import kz.tinkoff.homework_2.domain.model.ReactionParams
import kz.tinkoff.homework_2.domain.repository.MessageRepository

class RepoMessageImpl @Inject constructor(
    private val dataSource: MessageRemoteDataSource,
    private val mapper: MessageMapper,
    private val dtoMessageMapper: MessageDtoMapper,
    private val dtoReactionMapper: ReactionDtoMapper,
) : MessageRepository {

    override suspend fun getAllMessage(
        streamId: Int,
        stream: String,
        topic: String,
    ): List<MessageModel> {
        val response = dataSource.getAllMessage()
        val mappedResponse = mapper.map(response)
        return mappedResponse.filter {
            it.streamId == streamId && it.topic == topic && it.displayRecipient == stream
        }
    }

    override suspend fun sendMessage(params: MessageStreamParams) {
        dataSource.setMessageSend(dtoMessageMapper.map(from = params))
    }

    override suspend fun addReaction(messageId: Int, params: ReactionParams) {
        dataSource.addReaction(
            messageId = messageId,
            request = dtoReactionMapper.map(params)
        )
    }

    override suspend fun deleteReaction(messageId: Int, params: ReactionParams) {
        dataSource.deleteReaction(
            messageId = messageId,
            request = dtoReactionMapper.map(params)
        )
    }
}
