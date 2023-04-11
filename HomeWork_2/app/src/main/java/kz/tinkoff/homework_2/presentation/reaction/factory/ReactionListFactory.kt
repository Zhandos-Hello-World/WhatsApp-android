package kz.tinkoff.homework_2.presentation.reaction.factory

import kz.tinkoff.core.emoji.EmojiNCS

interface ReactionListFactory {

    fun createReactionList(): List<EmojiNCS>
}