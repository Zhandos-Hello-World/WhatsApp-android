package kz.tinkoff.homework_2.di_dagger.stream.modules

import dagger.Binds
import dagger.Module
import dagger.Provides
import kz.tinkoff.homework_2.data.datasource.DefaultStreamNetworkDataSource
import kz.tinkoff.homework_2.data.network.StreamApiService
import kz.tinkoff.homework_2.di_dagger.stream.StreamScope
import kz.tinkoff.homework_2.domain.datasource.StreamRemoteDataSource
import retrofit2.Retrofit

@Module(includes = [StreamNetworkModule.BindsStreamNetworkModule::class])
class StreamNetworkModule {

    @StreamScope
    @Provides
    fun provideMessageApiService(retrofit: Retrofit): StreamApiService {
        return retrofit.create(StreamApiService::class.java)
    }

    @Module
    interface BindsStreamNetworkModule {

        @Binds
        fun provideStreamRemoteDataSource(impl: DefaultStreamNetworkDataSource): StreamRemoteDataSource
    }
}
