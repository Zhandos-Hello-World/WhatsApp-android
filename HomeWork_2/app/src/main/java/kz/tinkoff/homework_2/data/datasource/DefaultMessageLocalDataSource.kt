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

    override fun getAllMessage(stream: String, topic: String): Flow<List<MessageModel>> {
        return dao.getAllMessages(stream, topic).map { it.map { mapperEntityMapper.map(it) } }
    }

    override fun getMessageByStreamId(streamId: Int): Flow<List<MessageModel>> {
        return dao.getMessageByStream(streamId).map { it.map { mapperEntityMapper.map(it) } }
    }

    override suspend fun addMessage(messageModel: MessageModel) {
        dao.addMessage(mapperEntityModelMapper.map(messageModel))
    }

    override suspend fun updateMessage(messageId: Int, emoji: String) {
        val entity = dao.getMessageById(messageId).first()
        val emojiCode = getUnicodeByEmoji(emoji)
        val reactions = entity.reactions.list.filter { emojiCode == it.emojiCode }.toMutableList()
        if (reactions.isEmpty()) {
            reactions += ReactionEntity(
                id = reactions.hashCode().toLong(),
                emojiName = "",
                emojiCode = emojiCode,
                reactionType = "",
                user = UserEntity(
                    id = MessageDvoMapper.MY_ID,
                    fullName = "Baimurat Zhandos",
                    email = "",
                    isMirrorDummy = false
                )
            )
        } else {
            val fromMe = reactions.find { it.user.id == MessageDvoMapper.MY_ID }
            if (fromMe != null) {
                reactions -= fromMe
            } else {
                reactions += ReactionEntity(
                    id = reactions.hashCode().toLong(),
                    emojiName = "",
                    emojiCode = emojiCode,
                    reactionType = "",
                    user = UserEntity(
                        id = MessageDvoMapper.MY_ID,
                        fullName = "Baimurat Zhandos",
                        email = "",
                        isMirrorDummy = false
                    )
                )
            }
        }
        dao.updateMessage(entity.copy(reactions = ReactionListEntity(reactions)))
    }

    private fun getUnicodeByEmoji(emoji: String): String {
        val codePoints = emoji.codePoints().toArray()
        return codePoints.joinToString(separator = "") { Integer.toHexString(it) }
    }
}
