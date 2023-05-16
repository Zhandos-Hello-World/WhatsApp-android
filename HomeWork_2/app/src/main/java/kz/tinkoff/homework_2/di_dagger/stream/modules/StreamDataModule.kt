package kz.tinkoff.homework_2.di_dagger.stream.modules

import dagger.Binds
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kz.tinkoff.homework_2.data.mappers.CreateStreamDomainToDataMapper
import kz.tinkoff.homework_2.data.mappers.StreamDataToDomainMapper
import kz.tinkoff.homework_2.data.mappers.SubscribedStreamDataToDomainMapper
import kz.tinkoff.homework_2.data.mappers.TopicDataToDomainMapper
import kz.tinkoff.homework_2.data.repository.StreamRepositoryImpl
import kz.tinkoff.homework_2.domain.repository.StreamRepository
import kz.tinkoff.homework_2.presentation.mapper.StreamDvoMapper
import kz.tinkoff.homework_2.presentation.mapper.SubscribedStreamDvoMapper

@Module(includes = [StreamDataModule.BindsStreamDataModule::class])
class StreamDataModule {


    @Provides
    fun provideStreamMapper(): StreamDataToDomainMapper {
        return StreamDataToDomainMapper()
    }

    @Provides
    fun provideTopicMapper(): TopicDataToDomainMapper {
        return TopicDataToDomainMapper()
    }

    @Provides
    fun provideDispatcherIOCoroutineScope(): CoroutineScope = CoroutineScope(Dispatchers.IO)


    @Provides
    fun provideDvoMapper(): StreamDvoMapper {
        return StreamDvoMapper()
    }

    @Provides
    fun provideSubscribedStreamMapper(): SubscribedStreamDataToDomainMapper {
        return SubscribedStreamDataToDomainMapper()
    }

    @Provides
    fun provideSubscribedStreamDvoMapper(): SubscribedStreamDvoMapper {
        return SubscribedStreamDvoMapper()
    }

    @Provides
    fun provideCreateStreamDtoMapper(): CreateStreamDomainToDataMapper {
        return CreateStreamDomainToDataMapper()
    }

    @Module
    interface BindsStreamDataModule {

        @Binds
        fun provideStreamRepository(impl: StreamRepositoryImpl): StreamRepository
    }
}
