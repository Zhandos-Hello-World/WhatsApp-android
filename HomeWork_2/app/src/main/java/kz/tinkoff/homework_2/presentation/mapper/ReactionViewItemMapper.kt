package kz.tinkoff.homework_2.presentation.mapper

import kz.tinkoff.core.Mapper
import kz.tinkoff.coreui.item.ReactionViewItem
import kz.tinkoff.homework_2.domain.model.MessageModel

class ReactionViewItemMapper : Mapper<MessageModel.ReactionModel, ReactionViewItem> {

    override fun map(from: MessageModel.ReactionModel): ReactionViewItem {
        return ReactionViewItem(
            id = from.reactionType.hashCode(),
            count = 1,
            emoji = getEmojiByUnicode(from.emojiCode),
            fromMe = from.user.id == MessageDvoMapper.MY_ID
        )
    }


    private fun getEmojiByUnicode(unicode: String): String {
        val unicodeInt = unicode.toInt(16)
        return String(Character.toChars(unicodeInt))
    }


    fun toReactionViewItems(from: List<MessageModel.ReactionModel>): MutableList<ReactionViewItem> {
        val fromMapped = from.map { map(it) }
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
}
