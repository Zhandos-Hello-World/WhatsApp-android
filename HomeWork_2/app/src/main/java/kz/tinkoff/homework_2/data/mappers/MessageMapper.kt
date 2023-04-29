package kz.tinkoff.homework_2.data.mappers

import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.jsonArray
import kotlinx.serialization.json.jsonPrimitive
import kz.tinkoff.core.Mapper
import kz.tinkoff.homework_2.data.model.MessageListResponse
import kz.tinkoff.homework_2.domain.model.MessageModel

class MessageMapper : Mapper<MessageListResponse, List<MessageModel>> {

    override fun map(from: MessageListResponse): List<MessageModel> {
        try {
            val mapped = from.messages.map { responseFrom ->
                MessageModel(
                    id = responseFrom.id,
                    senderId = responseFrom.senderId,
                    recipientId = responseFrom.recipientId,
                    timestamp = responseFrom.timestamp,
                    topic = responseFrom.topic,
                    isMeMessage = responseFrom.isMeMessage,
                    reactions = toReactionModel(responseFrom.reactions),
                    senderFullName = responseFrom.senderFullName,
                    senderEmail = responseFrom.senderEmail,
                    senderRealmStr = responseFrom.senderRealmStr,
                    displayRecipient = toDisplayRecipient(responseFrom.displayRecipient),
                    type = responseFrom.type,
                    streamId = responseFrom.streamId ?: -1,
                    avatarUrl = responseFrom.avatarUrl.orEmpty(),
                    content = responseFrom.content
                )
            }
            return mapped
        } catch (ex: Exception) {
            return listOf()
        }
    }

    private fun toDisplayRecipient(element: JsonElement?): String {
        return try {
            element?.jsonArray?.toString().orEmpty()
        } catch (ex: IllegalArgumentException) {
            element?.jsonPrimitive?.content.orEmpty()
        }
    }


    private fun toReactionModel(from: List<MessageListResponse.ReactionResponse>): List<MessageModel.ReactionModel> {
        return from.map { from ->
            MessageModel.ReactionModel(
                emojiCode = from.emojiCode,
                emojiName = from.emojiName,
                reactionType = from.reactionType,
                user = toUserModel(from.user)
            )
        }
    }


    private fun toUserModel(from: MessageListResponse.UserResponse): MessageModel.UserModel {
        return MessageModel.UserModel(id = from.id, fullName = from.fullName, email = from.email)
    }
}