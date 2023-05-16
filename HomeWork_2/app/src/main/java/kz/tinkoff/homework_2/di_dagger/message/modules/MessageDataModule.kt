package kz.tinkoff.homework_2.di_dagger.message.modules

import dagger.Binds
import dagger.Module
import dagger.Provides
import kz.tinkoff.homework_2.data.datasource.DefaultMessageLocalDataSource
import kz.tinkoff.homework_2.data.mappers.EditMessageDomainToDataMapper
import kz.tinkoff.homework_2.data.mappers.MessageParamsDomainToDataMapper
import kz.tinkoff.homework_2.data.mappers.MessageDbToDomainMapper
import kz.tinkoff.homework_2.data.mappers.MessageDataToDomainMapper
import kz.tinkoff.homework_2.data.mappers.MessageDomainToDbMapper
import kz.tinkoff.homework_2.data.mappers.ReactionParamsDomainToDataMapper
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
    fun provideMessageMapper(): MessageDataToDomainMapper {
        return MessageDataToDomainMapper()
    }

    @Provides
    fun provideMessageDvoMapper(reactionMapper: ReactionViewItemMapper): MessageDvoMapper {
        return MessageDvoMapper(reactionMapper)
    }

    @Provides
    fun provideMessageDtoMapper(): MessageParamsDomainToDataMapper {
        return MessageParamsDomainToDataMapper()
    }

    @Provides
    fun provideReactionMapper(): ReactionViewItemMapper {
        return ReactionViewItemMapper()
    }

    @Provides
    fun provideReactionDtoMapper(): ReactionParamsDomainToDataMapper {
        return ReactionParamsDomainToDataMapper()
    }

    @Provides
    fun provideMessageEntityMapper(): MessageDbToDomainMapper {
        return MessageDbToDomainMapper()
    }

    @Provides
    fun provideMessageModelEntityMapper(): MessageDomainToDbMapper {
        return MessageDomainToDbMapper()
    }

    @Provides
    fun provideEditDtoMessageMapper(): EditMessageDomainToDataMapper {
        return EditMessageDomainToDataMapper()
    }

    @Module
    interface BindsMessageDataModule {

        @Binds
        fun provideMessageLocalDataSource(impl: DefaultMessageLocalDataSource): MessageLocalDataSource

        @Binds
        fun provideMessageRepository(impl: MessageRepositoryImpl): MessageRepository
    }
}
