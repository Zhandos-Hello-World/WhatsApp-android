package kz.tinkoff.homework_2.presentation.mapper

import java.util.Locale
import kz.tinkoff.core.Mapper
import kz.tinkoff.core.adapter.DelegateItem
import kz.tinkoff.coreui.custom.dvo.MessageDvo
import kz.tinkoff.homework_2.domain.model.MessageModel
import kz.tinkoff.homework_2.presentation.delegates.date.DateDelegateItem
import kz.tinkoff.homework_2.presentation.delegates.date.DateModel
import kz.tinkoff.homework_2.presentation.delegates.message.MessageDelegateItem
import org.threeten.bp.Instant
import org.threeten.bp.LocalDateTime
import org.threeten.bp.ZoneId
import org.threeten.bp.format.DateTimeFormatter

class MessageDelegateItemMapper(private val dvoMapper: MessageDvoMapper): Mapper<List<MessageDvo>, List<DelegateItem>> {

    override fun map(from: List<MessageDvo>): List<DelegateItem> {
        val sortedMessages = from.sortedBy { it.timestamp }
        val delegateItemList = buildList {
            val firstMessage = sortedMessages.first()

            var date = DateModel(
                timestamp = firstMessage.timestamp,
                date = getDateFromTimestamp(firstMessage.timestamp)
            )
            add(DateDelegateItem(date))

            sortedMessages.forEach { messageDvo ->
                val nextDate = DateModel(
                    timestamp = messageDvo.timestamp,
                    date = getDateFromTimestamp(messageDvo.timestamp)
                )
                if (date.date != nextDate.date) {
                    date = nextDate
                    add(DateDelegateItem(date))
                }
                add(toMessageDelegate(messageDvo))
            }
        }
        return delegateItemList
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
        return map(from.map { dvoMapper.map(it) })
    }


    fun getDateFromTimestamp(timestamp: Long): String {
        val dateTime = LocalDateTime.ofInstant(Instant.ofEpochSecond(timestamp), ZoneId.of("UTC"))
        val formatter = DateTimeFormatter.ofPattern("d MMM", Locale.ENGLISH)
        return dateTime.format(formatter)
    }
}
