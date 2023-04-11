package kz.tinkoff.homework_2.data.mappers

import kz.tinkoff.core.Mapper
import kz.tinkoff.homework_2.data.model.MessageListResponse
import kz.tinkoff.homework_2.domain.model.MessageModel

class MessageMapper : Mapper<MessageListResponse, List<MessageModel>> {

    override fun map(from: MessageListResponse): List<MessageModel> {
        return from.messages.map { responseFrom ->
            MessageModel(id = responseFrom.id,
                senderId = responseFrom.senderId,
                recipientId = responseFrom.recipientId,
                timestamp = responseFrom.timestamp,
                topic = responseFrom.topic,
                isMeMessage = responseFrom.isMeMessage,
                reactions = toReactionModel(responseFrom.reactions),
                senderFullName = responseFrom.senderFullName,
                senderEmail = responseFrom.senderEmail,
                senderRealmStr = responseFrom.senderRealmStr,
                displayRecipient = responseFrom.displayRecipient.toString().orEmpty(),
                type = responseFrom.type,
                streamId = responseFrom.streamId.orEmpty(),
                avatarUrl = responseFrom.avatarUrl,
                content = responseFrom.content
            )
        }
    }

    private fun toReactionModel(from: List<MessageListResponse.ReactionResponse>): List<MessageModel.ReactionModel> {
        return from.map { from ->
            MessageModel.ReactionModel(emojiCode = from.emojiCode,
                emojiName = from.emojiName,
                reactionType = from.reactionType,
                user = toUserModel(from.user))
        }
    }


    private fun toUserModel(from: MessageListResponse.UserResponse): MessageModel.UserModel {
        return MessageModel.UserModel(id = from.id, fullName = from.fullName, email = from.email)
    }
}