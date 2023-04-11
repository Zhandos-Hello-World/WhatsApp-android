package kz.tinkoff.coreui.custom.dvo

import kz.tinkoff.coreui.item.ReactionViewItem

data class MessageDvo(
    val id: Int,
    val senderId: Long,
    val timestamp: Long,
    val isMeMessage: Boolean,
    var reactions: MutableList<ReactionViewItem>,
    val senderFullName: String,
    val content: String,
    val avatarUrl: String
)