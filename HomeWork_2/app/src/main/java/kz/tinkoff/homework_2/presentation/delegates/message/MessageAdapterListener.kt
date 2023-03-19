package kz.tinkoff.homework_2.presentation.delegates.message

import kz.tinkoff.coreui.item.ReactionViewItem

interface MessageAdapterListener {

    fun addReactionClickListener(model: MessageModel)

    fun setEmojiClickListener(model: MessageModel, viewItem: ReactionViewItem)
}