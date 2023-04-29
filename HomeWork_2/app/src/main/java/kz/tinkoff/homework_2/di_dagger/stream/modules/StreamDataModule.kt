package kz.tinkoff.homework_2.di_dagger.stream.modules

import com.github.terrakok.cicerone.Router
import dagger.Binds
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kz.tinkoff.homework_2.data.mappers.StreamMapper
import kz.tinkoff.homework_2.data.mappers.TopicMapper
import kz.tinkoff.homework_2.data.repository.RepoStreamImpl
import kz.tinkoff.homework_2.domain.repository.StreamRepository
import kz.tinkoff.homework_2.presentation.channels.elm.StreamActor
import kz.tinkoff.homework_2.presentation.channels.elm.StreamStoreFactory
import kz.tinkoff.homework_2.presentation.mapper.StreamDvoMapper

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
    fun provideChannelFactory(actor: StreamActor, router: Router): StreamStoreFactory {
        return StreamStoreFactory(
            actor,
            router = router
        )
    }

    @Module
    interface BindsStreamDataModule {

        @Binds
        fun provideStreamRepository(impl: RepoStreamImpl): StreamRepository
    }
}
