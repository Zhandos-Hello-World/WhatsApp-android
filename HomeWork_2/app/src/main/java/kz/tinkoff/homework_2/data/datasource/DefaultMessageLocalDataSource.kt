package kz.tinkoff.homework_2.data.datasource

import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kz.tinkoff.homework_2.data.dao.MessageDao
import kz.tinkoff.homework_2.data.enitiy.ReactionEntity
import kz.tinkoff.homework_2.data.enitiy.ReactionListEntity
import kz.tinkoff.homework_2.data.enitiy.UserEntity
import kz.tinkoff.homework_2.data.mappers.MessageEntityMapper
import kz.tinkoff.homework_2.data.mappers.MessageModelEntityMapper
import kz.tinkoff.homework_2.domain.datasource.MessageLocalDataSource
import kz.tinkoff.homework_2.domain.model.MessageModel
import kz.tinkoff.homework_2.presentation.mapper.MessageDvoMapper

class DefaultMessageLocalDataSource @Inject constructor(
    private val dao: MessageDao,
    private val mapperEntityMapper: MessageEntityMapper,
    private val mapperEntityModelMapper: MessageModelEntityMapper,
) : MessageLocalDataSource {

    override suspend fun getAllMessage(stream: String, topic: String): List<MessageModel> {
        return dao.getAllMessages(stream, topic).map { mapperEntityMapper.map(it) }
    }

    override fun getMessageByStreamId(streamId: Int): Flow<List<MessageModel>> {
        return dao.getMessageByStream(streamId).map { it.map { mapperEntityMapper.map(it) } }
    }

    override suspend fun addMessage(messageModel: MessageModel) {
        dao.addMessage(mapperEntityModelMapper.map(messageModel))
    }
}