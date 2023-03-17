package kz.tinkoff.coreui.item

data class ReactionViewItem(
    val id: Int,
    val count: Int,
    val emoji: String,
    var fromMe: Boolean = false
) {
    companion object {
        fun defaultReaction(): ReactionViewItem {
            return ReactionViewItem(
                id = 0,
                count = 1,
                emoji = "+"
            )
        }
    }
}