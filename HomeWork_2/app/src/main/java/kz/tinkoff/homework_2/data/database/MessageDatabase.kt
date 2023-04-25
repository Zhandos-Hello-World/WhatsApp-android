package kz.tinkoff.homework_2.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import kz.tinkoff.homework_2.data.dao.MessageDao
import kz.tinkoff.homework_2.data.enitiy.MessageEntity

@Database(
    entities = [MessageEntity::class],
    version = 1,
    exportSchema = false
)
abstract class MessageDatabase : RoomDatabase() {
    abstract fun dao(): MessageDao

}
