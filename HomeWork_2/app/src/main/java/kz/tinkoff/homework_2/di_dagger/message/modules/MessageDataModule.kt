package kz.tinkoff.homework_2.di_dagger.message.modules

import dagger.Binds
import dagger.Module
import dagger.Provides
import kz.tinkoff.homework_2.data.datasource.DefaultMessageLocalDataSource
import kz.tinkoff.homework_2.data.mappers.EditDtoMessageMapper
import kz.tinkoff.homework_2.data.mappers.MessageDtoMapper
import kz.tinkoff.homework_2.data.mappers.MessageEntityMapper
import kz.tinkoff.homework_2.data.mappers.MessageMapper
import kz.tinkoff.homework_2.data.mappers.MessageModelEntityMapper
import kz.tinkoff.homework_2.data.mappers.ReactionDtoMapper
import kz.tinkoff.homework_2.data.repository.MessageRepositoryImpl
import kz.tinkoff.homework_2.domain.datasource.MessageLocalDataSource
import kz.tinkoff.homework_2.domain.repository.MessageRepository
import kz.tinkoff.homework_2.presentation.mapper.MessageDelegateItemMapper
import kz.tinkoff.homework_2.presentation.mapper.MessageDvoMapper
import kz.tinkoff.homework_2.presentation.mapper.ReactionViewItemMapper

@Module(includes = [MessageDataModule.BindsMessageDataModule::class])
class MessageDataModule {

    @Provides
    fun provideMessageDelegateItemMapper(
        dvoMapper: MessageDvoMapper,
    ): MessageDelegateItemMapper {
        return MessageDelegateItemMapper(
            dvoMapper
        )
    }

    @Provides
    fun provideMessageMapper(): MessageMapper {
        return MessageMapper()
    }

    @Provides
    fun provideMessageDvoMapper(reactionMapper: ReactionViewItemMapper): MessageDvoMapper {
        return MessageDvoMapper(reactionMapper)
    }

    @Provides
    fun provideMessageDtoMapper(): MessageDtoMapper {
        return MessageDtoMapper()
    }

    @Provides
    fun provideReactionMapper(): ReactionViewItemMapper {
        return ReactionViewItemMapper()
    }

    @Provides
    fun provideReactionDtoMapper(): ReactionDtoMapper {
        return ReactionDtoMapper()
    }

    @Provides
    fun provideMessageEntityMapper(): MessageEntityMapper {
        return MessageEntityMapper()
    }

    @Provides
    fun provideMessageModelEntityMapper(): MessageModelEntityMapper {
        return MessageModelEntityMapper()
    }

    @Provides
    fun provideEditDtoMessageMapper(): EditDtoMessageMapper {
        return EditDtoMessageMapper()
    }

    @Module
    interface BindsMessageDataModule {

        @Binds
        fun provideMessageLocalDataSource(impl: DefaultMessageLocalDataSource): MessageLocalDataSource

        @Binds
        fun provideMessageRepository(impl: MessageRepositoryImpl): MessageRepository
    }
}
