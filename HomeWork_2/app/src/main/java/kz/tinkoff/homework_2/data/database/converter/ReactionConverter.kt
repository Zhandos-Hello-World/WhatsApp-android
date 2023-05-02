package kz.tinkoff.homework_2.data.database.converter

import androidx.room.TypeConverter
import com.google.gson.Gson
import kz.tinkoff.homework_2.data.enitiy.ReactionListEntity
import kz.tinkoff.homework_2.data.enitiy.UserEntity

class ReactionConverter {

    @TypeConverter
    fun fromReactionToJson(list: ReactionListEntity): String {
        return Gson().toJson(list)
    }

    @TypeConverter
    fun fromJsonToReaction(json: String): ReactionListEntity {
        return Gson().fromJson(json, ReactionListEntity::class.java)
    }
}
