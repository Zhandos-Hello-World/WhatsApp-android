package kz.tinkoff.homework_2.presentation.reaction.factory

import kz.tinkoff.core.emoji.EmojiNCS
import kz.tinkoff.core.emoji.emojiSetNCS

class DefaultReactionListFactory: ReactionListFactory {


    override fun createReactionList(): List<EmojiNCS> {
        return emojiSetNCS
    }

}