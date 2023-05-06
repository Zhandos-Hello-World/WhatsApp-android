package kz.tinkoff.homework_2.presentation.delegates.message

import kz.tinkoff.coreui.item.ReactionViewItem

interface MessageAdapterListener {

    fun addReactionClickListener(position: Int)

    fun setEmojiClickListener(position: Int, viewItem: ReactionViewItem)
}
