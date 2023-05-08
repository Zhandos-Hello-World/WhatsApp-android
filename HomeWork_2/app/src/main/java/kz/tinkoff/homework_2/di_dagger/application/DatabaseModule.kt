package kz.tinkoff.homework_2.di_dagger.application

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import javax.inject.Singleton
import kz.tinkoff.homework_2.data.dao.MessageDao
import kz.tinkoff.homework_2.data.database.MessageDatabase
import kz.tinkoff.homework_2.data.database.converter.ReactionConverter

@Module
class DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(context: Context): MessageDatabase {
        return Room.databaseBuilder(
            context,
            MessageDatabase::class.java,
            DATABASE_NAME
        ).build()
    }

    @Provides
    @Singleton
    fun provideDao(messageDb: MessageDatabase): MessageDao {
        return messageDb.dao()
    }


    companion object {
        const val DATABASE_NAME = "MESSAGE_DB"
    }
}
