package kz.tinkoff.homework_2.data.mappers

import kz.tinkoff.core.Mapper
import kz.tinkoff.homework_2.data.enitiy.MessageEntity
import kz.tinkoff.homework_2.data.enitiy.ReactionEntity
import kz.tinkoff.homework_2.data.enitiy.ReactionListEntity
import kz.tinkoff.homework_2.data.enitiy.UserEntity
import kz.tinkoff.homework_2.domain.model.MessageModel

class MessageDomainToDbMapper : Mapper<MessageModel, MessageEntity> {

    override fun map(from: MessageModel): MessageEntity {
        return MessageEntity(
            id = from.id,
            senderId = from.senderId,
            recipientId = from.recipientId,
            timestamp = from.timestamp,
            topic = from.topic,
            isMeMessage = from.isMeMessage,
            senderFullName = from.senderFullName,
            senderEmail = from.senderEmail,
            senderRealmStr = from.senderRealmStr,
            displayRecipient = from.displayRecipient,
            type = from.type,
            content = from.content,
            streamId = from.streamId,
            avatarUrl = from.avatarUrl,
            reactions = ReactionListEntity(from.reactions.map { toReactionEntity(it) })
        )
    }

    private fun toReactionEntity(from: MessageModel.ReactionModel): ReactionEntity {
        return ReactionEntity(
            emojiName = from.emojiName,
            emojiCode = from.emojiCode,
            reactionType = from.reactionType,
            user = toUserModel(from.user)
        )
    }

    private fun toUserModel(from: MessageModel.UserModel): UserEntity {
        return UserEntity(
            id = from.id,
            fullName = from.fullName,
            email = from.email
        )
    }
}
