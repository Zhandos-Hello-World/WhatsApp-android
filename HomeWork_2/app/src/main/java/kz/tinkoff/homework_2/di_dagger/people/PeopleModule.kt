package kz.tinkoff.homework_2.di_dagger.people

import dagger.Binds
import dagger.Module
import dagger.Provides
import kz.tinkoff.homework_2.data.datasource.DefaultPeopleNetworkDataSource
import kz.tinkoff.homework_2.data.mappers.PersonMapper
import kz.tinkoff.homework_2.data.mappers.PresenceMapper
import kz.tinkoff.homework_2.data.mappers.ProfileMapper
import kz.tinkoff.homework_2.data.network.PeopleApiService
import kz.tinkoff.homework_2.data.repository.RepoPeopleImpl
import kz.tinkoff.homework_2.domain.datasource.PeopleRemoteDataSource
import kz.tinkoff.homework_2.domain.repository.PeopleRepository
import kz.tinkoff.homework_2.presentation.mapper.PersonDelegateItemMapper
import kz.tinkoff.homework_2.presentation.mapper.PersonDvoMapper
import kz.tinkoff.homework_2.presentation.mapper.ProfileDvoMapper
import retrofit2.Retrofit

@Module(includes = [PeopleModule.BindsPeopleModule::class])
class PeopleModule {

    @PeopleScope
    @Provides
    fun providePeopleApiService(retrofit: Retrofit): PeopleApiService {
        return retrofit.create(PeopleApiService::class.java)
    }

    @Provides
    fun providePersonMapper(): PersonMapper {
        return PersonMapper()
    }

    @Provides
    fun provideProfileMapper(): ProfileMapper {
        return ProfileMapper()
    }

    @Provides
    fun providePresenceMapper(): PresenceMapper {
        return PresenceMapper()
    }

    @Provides
    fun provideDvoMapper(): ProfileDvoMapper {
        return ProfileDvoMapper()
    }

    @Provides
    fun providePersonDelegateItemMapper(): PersonDelegateItemMapper {
        return PersonDelegateItemMapper()
    }

    @Provides
    fun providePersonDvoMapper(delegateItemMapper: PersonDelegateItemMapper): PersonDvoMapper {
        return PersonDvoMapper(delegateItemMapper)
    }


    @Module
    interface BindsPeopleModule {

        @Binds
        fun provideProfileRepository(impl: RepoPeopleImpl): PeopleRepository

        @Binds
        fun providePeopleDataSource(impl: DefaultPeopleNetworkDataSource): PeopleRemoteDataSource
    }

}
