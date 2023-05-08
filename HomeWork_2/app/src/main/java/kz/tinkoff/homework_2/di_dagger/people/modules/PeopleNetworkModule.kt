package kz.tinkoff.homework_2.di_dagger.people.modules

import dagger.Binds
import dagger.Module
import dagger.Provides
import kz.tinkoff.homework_2.data.datasource.DefaultPeopleNetworkDataSource
import kz.tinkoff.homework_2.data.network.PeopleApiService
import kz.tinkoff.homework_2.di_dagger.people.PeopleScope
import kz.tinkoff.homework_2.domain.datasource.PeopleRemoteDataSource
import retrofit2.Retrofit

@Module(includes = [PeopleNetworkModule.BindsPeopleNetworkModule::class])
class PeopleNetworkModule {

    @PeopleScope
    @Provides
    fun providePeopleApiService(retrofit: Retrofit): PeopleApiService {
        return retrofit.create(PeopleApiService::class.java)
    }

    @Module
    interface BindsPeopleNetworkModule {
        @Binds
        fun providePeopleDataSource(impl: DefaultPeopleNetworkDataSource): PeopleRemoteDataSource
    }
}
