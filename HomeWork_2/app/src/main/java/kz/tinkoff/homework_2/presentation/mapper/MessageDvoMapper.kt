package kz.tinkoff.homework_2.presentation.mapper


import kz.tinkoff.core.Mapper
import kz.tinkoff.coreui.custom.dvo.MessageDvo
import kz.tinkoff.homework_2.domain.model.MessageModel


class MessageDvoMapper(private val reactionViewItemMapper: ReactionViewItemMapper) :
    Mapper<MessageModel, MessageDvo> {

    override fun map(from: MessageModel): MessageDvo {
        return MessageDvo(
            id = from.id,
            senderId = from.senderId,
            timestamp = from.timestamp,
            isMeMessage = from.isMeMessage,
            reactions = reactionViewItemMapper.toReactionViewItems(from.reactions),
            senderFullName = from.senderFullName,
            avatarUrl = from.avatarUrl,
            content = from.content,
        )
    }

    companion object {
        const val MY_ID = 604581
    }
}
