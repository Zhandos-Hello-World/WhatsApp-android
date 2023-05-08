package kz.tinkoff.homework_2.di_dagger.message

import dagger.Binds
import dagger.Module
import dagger.Provides
import kz.tinkoff.homework_2.data.datasource.DefaultMessageNetworkDataSource
import kz.tinkoff.homework_2.data.mappers.MessageDtoMapper
import kz.tinkoff.homework_2.data.mappers.MessageMapper
import kz.tinkoff.homework_2.data.mappers.ReactionDtoMapper
import kz.tinkoff.homework_2.data.network.MessageApiService
import kz.tinkoff.homework_2.data.repository.RepoMessageImpl
import kz.tinkoff.homework_2.domain.datasource.MessageRemoteDataSource
import kz.tinkoff.homework_2.domain.repository.MessageRepository
import kz.tinkoff.homework_2.presentation.mapper.MessageDelegateItemMapper
import kz.tinkoff.homework_2.presentation.mapper.MessageDvoMapper
import kz.tinkoff.homework_2.presentation.mapper.ReactionViewItemMapper
import retrofit2.Retrofit

@Module(includes = [MessageModule.BindsMessageModule::class])
class MessageModule {

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

    @Module
    interface BindsMessageModule {

        @Binds
        fun provideMessageRepository(impl: RepoMessageImpl): MessageRepository
    }
}
