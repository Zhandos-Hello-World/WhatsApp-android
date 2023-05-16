package kz.tinkoff.homework_2.data.mappers

import kz.tinkoff.core.Mapper
import kz.tinkoff.homework_2.data.enitiy.MessageEntity
import kz.tinkoff.homework_2.data.enitiy.ReactionEntity
import kz.tinkoff.homework_2.data.enitiy.UserEntity
import kz.tinkoff.homework_2.domain.model.MessageModel

class MessageDbToDomainMapper : Mapper<MessageEntity, MessageModel> {

    override fun map(from: MessageEntity): MessageModel {
        return MessageModel(
            id = from.id,
            senderId = from.senderId,
            recipientId = from.recipientId,
            timestamp = from.timestamp,
            topic = from.topic,
            isMeMessage = from.isMeMessage,
            reactions = toReactionModels(from.reactions.list),
            senderFullName = from.senderFullName,
            senderEmail = from.senderEmail,
            senderRealmStr = from.senderRealmStr,
            content = from.content,
            displayRecipient = from.displayRecipient.orEmpty(),
            type = from.type,
            streamId = from.streamId ?: 0,
            avatarUrl = from.avatarUrl.orEmpty(),
        )
    }

    private fun toReactionModels(from: List<ReactionEntity>): List<MessageModel.ReactionModel> {
        return from.map { toReactionModel(it) }
    }

    private fun toReactionModel(from: ReactionEntity): MessageModel.ReactionModel {
        return MessageModel.ReactionModel(
            emojiName = from.emojiName,
            emojiCode = from.emojiCode,
            reactionType = from.reactionType,
            user = toUserModel(from.user)
        )
    }

    private fun toUserModel(from: UserEntity): MessageModel.UserModel {
        return MessageModel.UserModel(
            id = from.id,
            fullName = from.fullName,
            email = from.email
        )
    }
}
