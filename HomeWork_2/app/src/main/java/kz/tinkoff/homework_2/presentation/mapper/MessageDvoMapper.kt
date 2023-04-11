package kz.tinkoff.homework_2.presentation.mapper


import java.util.Locale
import kz.tinkoff.core.Mapper
import kz.tinkoff.core.adapter.DelegateItem
import kz.tinkoff.coreui.custom.dvo.MessageDvo
import kz.tinkoff.coreui.item.ReactionViewItem
import kz.tinkoff.homework_2.domain.model.MessageModel
import kz.tinkoff.homework_2.presentation.delegates.date.DateDelegateItem
import kz.tinkoff.homework_2.presentation.delegates.date.DateModel
import kz.tinkoff.homework_2.presentation.delegates.message.MessageDelegateItem
import org.threeten.bp.Instant
import org.threeten.bp.LocalDateTime
import org.threeten.bp.ZoneId
import org.threeten.bp.format.DateTimeFormatter

class MessageDvoMapper : Mapper<MessageModel, MessageDvo> {

    override fun map(from: MessageModel): MessageDvo {
        return MessageDvo(id = from.id,
            senderId = from.senderId,
            timestamp = from.timestamp,
            isMeMessage = from.isMeMessage,
            reactions = toReactionViewItems(from.reactions),
            senderFullName = from.senderFullName,
            avatarUrl = from.avatarUrl,
            content = from.content,
        )
    }

    private fun toReactionViewItems(from: List<MessageModel.ReactionModel>): MutableList<ReactionViewItem> {
        val fromMapped = from.map { toReactionViewItem(it) }
        val filterReactions = buildList<ReactionViewItem> {
            fromMapped.forEach { reactionViewItem ->
                val index = indexOfLast { it.emoji == reactionViewItem.emoji }

                if (index != -1) {
                    val existingReaction = get(index)
                    set(index, existingReaction.copy(count = existingReaction.count + 1))
                } else {
                    add(reactionViewItem)
                }
            }
        }
        return filterReactions.toMutableList()
    }

    private fun toReactionViewItem(from: MessageModel.ReactionModel): ReactionViewItem {
        return ReactionViewItem(id = from.reactionType.hashCode(),
            count = 1,
            emoji = getEmojiByUnicode(from.emojiCode),
            fromMe = from.user.id == MY_ID
            )
    }

    private fun getEmojiByUnicode(unicode: String): String {
        val unicodeInt = unicode.toInt(16)
        return String(Character.toChars(unicodeInt))
    }

    fun toMessageDelegate(from: MessageDvo): DelegateItem {
        return MessageDelegateItem(
            id = from.id,
            value = from
        )
    }

    fun toMessageWithDateFromModel(from: List<MessageModel>): List<DelegateItem> {
        if (from.isEmpty()) {
            return emptyList()
        }
        return toMessageWithDateFromDvo(from.map { map(it) })
    }

    fun toMessageWithDateFromDvo(from: List<MessageDvo>): List<DelegateItem> {
        val sortedMessages = from.sortedBy { it.timestamp }
        val delegateItemList = buildList {
            val firstMessage = sortedMessages.first()

            var date = DateModel(timestamp = firstMessage.timestamp,
                date = getDateFromTimestamp(firstMessage.timestamp))
            add(DateDelegateItem(date))

            sortedMessages.forEach { messageDvo ->
                val nextDate = DateModel(timestamp = messageDvo.timestamp,
                    date = getDateFromTimestamp(messageDvo.timestamp))
                if (date.date != nextDate.date) {
                    date = nextDate
                    add(DateDelegateItem(date))
                }
                add(toMessageDelegate(messageDvo))
            }
        }
        return delegateItemList
    }

    private fun getDateFromTimestamp(timestamp: Long): String {
        val dateTime = LocalDateTime.ofInstant(Instant.ofEpochSecond(timestamp), ZoneId.of("UTC"))
        val formatter = DateTimeFormatter.ofPattern("d MMM", Locale.ENGLISH)
        return dateTime.format(formatter)

    }

    companion object {
        const val MY_ID = 604581
    }
}