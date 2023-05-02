package kz.tinkoff.homework_2.data.database.converter

import androidx.room.TypeConverter
import com.google.gson.Gson
import kz.tinkoff.homework_2.data.enitiy.UserEntity

class UserConverter {

    @TypeConverter
    fun fromUserToJson(user: UserEntity): String {
        return Gson().toJson(user)
    }

    @TypeConverter
    fun fromJsonToUser(json: String): UserEntity {
        return Gson().fromJson(json, UserEntity::class.java)
    }
}
