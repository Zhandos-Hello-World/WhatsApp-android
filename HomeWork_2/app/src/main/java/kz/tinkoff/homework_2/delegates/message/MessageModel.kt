package kz.tinkoff.homework_2.delegates.message

import kz.tinkoff.coreui.item.ReactionViewItem

data class MessageModel(
    val id: Int,
    val fullName: String,
    val message: String,
    val date: String,
    var reactions: List<ReactionViewItem>,
)