package kz.tinkoff.homework_2.custom.item

data class ReactionViewItem(
    val id: Int,
    val count: Int,
    val emoji: String
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