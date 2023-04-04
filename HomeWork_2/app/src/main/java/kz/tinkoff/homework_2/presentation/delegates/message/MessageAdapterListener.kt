package kz.tinkoff.homework_2.presentation.delegates.message

import kz.tinkoff.coreui.custom.dvo.MessageDvo
import kz.tinkoff.coreui.item.ReactionViewItem

interface MessageAdapterListener {

    fun addReactionClickListener(model: MessageDvo)

    fun setEmojiClickListener(model: MessageDvo, viewItem: ReactionViewItem)
}