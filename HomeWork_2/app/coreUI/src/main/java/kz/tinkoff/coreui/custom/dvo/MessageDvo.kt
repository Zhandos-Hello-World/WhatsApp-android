package kz.tinkoff.coreui.custom.dvo

import kz.tinkoff.coreui.item.ReactionViewItem

data class MessageDvo(
    val id: Int,
    val title: String,
    val message: String,
    val date: String,
    val reactions: List<ReactionViewItem>
)