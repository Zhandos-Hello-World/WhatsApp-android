package kz.tinkoff.homework_2.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import kz.tinkoff.homework_2.data.dao.MessageDao
import kz.tinkoff.homework_2.data.database.converter.ReactionConverter
import kz.tinkoff.homework_2.data.database.converter.UserConverter
import kz.tinkoff.homework_2.data.enitiy.MessageEntity

@TypeConverters(value = [ReactionConverter::class, UserConverter::class])
@Database(
    entities = [MessageEntity::class],
    version = 1,
    exportSchema = false
)
abstract class MessageDatabase : RoomDatabase() {
    abstract fun dao(): MessageDao

}
