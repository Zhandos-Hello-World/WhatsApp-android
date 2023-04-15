package kz.tinkoff.homework_2.data.mappers

import kz.tinkoff.core.Mapper
import kz.tinkoff.homework_2.domain.model.ReactionParams

class ReactionDtoMapper: Mapper<ReactionParams, HashMap<String, String>> {

    override fun map(from: ReactionParams): HashMap<String, String> {
        val filters = hashMapOf<String, String>()

        filters[EMOJI_NAME] = from.emojiName
        return filters
    }

    // private
    companion object {
        const val EMOJI_NAME = "emoji_name"
        const val EMOJI_CODE = "emoji_code"
        const val REACTION_TYPE = "reaction_type"
    }
}
