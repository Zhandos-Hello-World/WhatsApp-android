package kz.tinkoff.homework_2.data.database.converter

import androidx.room.TypeConverter
import com.google.gson.Gson
import kz.tinkoff.homework_2.data.enitiy.ReactionListEntity
import kz.tinkoff.homework_2.data.enitiy.UserEntity

class ReactionConverter {

    @TypeConverter
    fun fromUserToJson(user: UserEntity): String {
        return Gson().toJson(user)
    }

    @TypeConverter
    fun fromJsonToUser(json: String): UserEntity {
        return Gson().fromJson(json, UserEntity::class.java)
    }

    @TypeConverter
    fun fromReactionToJson(list: ReactionListEntity): String {
        return Gson().toJson(list)
    }

    @TypeConverter
    fun fromJsonToReaction(json: String): ReactionListEntity {
        return Gson().fromJson(json, ReactionListEntity::class.java)
    }
}
