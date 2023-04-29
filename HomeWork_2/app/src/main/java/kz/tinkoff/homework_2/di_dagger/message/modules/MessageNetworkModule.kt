package kz.tinkoff.homework_2.di_dagger.message.modules

import dagger.Binds
import dagger.Module
import dagger.Provides
import kz.tinkoff.homework_2.data.datasource.DefaultMessageNetworkDataSource
import kz.tinkoff.homework_2.data.network.MessageApiService
import kz.tinkoff.homework_2.di_dagger.message.MessageScope
import kz.tinkoff.homework_2.domain.datasource.MessageRemoteDataSource
import retrofit2.Retrofit

@Module(includes = [MessageNetworkModule.BindsMessageNetworkModule::class])
class MessageNetworkModule {

    @MessageScope
    @Provides
    fun provideMessageApiService(retrofit: Retrofit): MessageApiService {
        return retrofit.create(MessageApiService::class.java)
    }

    @Module
    interface BindsMessageNetworkModule {

        @Binds
        fun provideMessageRemoteDataSource(impl: DefaultMessageNetworkDataSource): MessageRemoteDataSource

    }
}
