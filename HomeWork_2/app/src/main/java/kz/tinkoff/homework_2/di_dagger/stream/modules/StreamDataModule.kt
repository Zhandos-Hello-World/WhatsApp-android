package kz.tinkoff.homework_2.di_dagger.stream.modules

import com.github.terrakok.cicerone.Router
import dagger.Binds
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kz.tinkoff.homework_2.data.mappers.CreateStreamDtoMapper
import kz.tinkoff.homework_2.data.mappers.StreamMapper
import kz.tinkoff.homework_2.data.mappers.SubscribedStreamMapper
import kz.tinkoff.homework_2.data.mappers.TopicMapper
import kz.tinkoff.homework_2.data.repository.StreamRepositoryImpl
import kz.tinkoff.homework_2.domain.repository.StreamRepository
import kz.tinkoff.homework_2.presentation.create_stream.elm.CreateStreamActor
import kz.tinkoff.homework_2.presentation.create_stream.elm.CreateStreamStoreFactory
import kz.tinkoff.homework_2.presentation.mapper.StreamDvoMapper
import kz.tinkoff.homework_2.presentation.mapper.SubscribedStreamDvoMapper
import kz.tinkoff.homework_2.presentation.streams.elm.StreamActor
import kz.tinkoff.homework_2.presentation.streams.elm.StreamStoreFactory

@Module(includes = [StreamDataModule.BindsStreamDataModule::class])
class StreamDataModule {


    @Provides
    fun provideStreamMapper(): StreamMapper {
        return StreamMapper()
    }

    @Provides
    fun provideTopicMapper(): TopicMapper {
        return TopicMapper()
    }

    @Provides
    fun provideDispatcherIOCoroutineScope(): CoroutineScope = CoroutineScope(Dispatchers.IO)


    @Provides
    fun provideDvoMapper(): StreamDvoMapper {
        return StreamDvoMapper()
    }

    @Provides
    fun provideSubscribedStreamMapper(): SubscribedStreamMapper {
        return SubscribedStreamMapper()
    }

    @Provides
    fun provideSubscribedStreamDvoMapper(): SubscribedStreamDvoMapper {
        return SubscribedStreamDvoMapper()
    }

    @Provides
    fun provideCreateStreamDtoMapper(): CreateStreamDtoMapper {
        return CreateStreamDtoMapper()
    }

    @Provides
    fun provideStreamsFactory(actor: StreamActor, router: Router): StreamStoreFactory {
        return StreamStoreFactory(
            actor = actor,
            router = router
        )
    }

    @Provides
    fun provideCreateStreamFactory(
        actor: CreateStreamActor,
        router: Router,
    ): CreateStreamStoreFactory {
        return CreateStreamStoreFactory(
            actor = actor,
            router
        )
    }

    @Module
    interface BindsStreamDataModule {

        @Binds
        fun provideStreamRepository(impl: StreamRepositoryImpl): StreamRepository
    }
}
