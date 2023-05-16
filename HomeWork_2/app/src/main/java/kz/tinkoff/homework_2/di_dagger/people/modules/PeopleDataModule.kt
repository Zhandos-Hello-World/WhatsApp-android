package kz.tinkoff.homework_2.di_dagger.people.modules

import dagger.Binds
import dagger.Module
import dagger.Provides
import kz.tinkoff.homework_2.data.mappers.PersonDataToDomainMapper
import kz.tinkoff.homework_2.data.mappers.PresenceDataToDomainMapper
import kz.tinkoff.homework_2.data.mappers.ProfileDataToModelMapper
import kz.tinkoff.homework_2.data.repository.PeopleRepositoryImpl
import kz.tinkoff.homework_2.domain.repository.PeopleRepository
import kz.tinkoff.homework_2.presentation.mapper.PersonDelegateItemMapper
import kz.tinkoff.homework_2.presentation.mapper.PersonDvoMapper
import kz.tinkoff.homework_2.presentation.mapper.ProfileDvoMapper

@Module(includes = [PeopleDataModule.BindsPeopleDataModule::class])
class PeopleDataModule {
    @Provides
    fun providePersonMapper(): PersonDataToDomainMapper {
        return PersonDataToDomainMapper()
    }

    @Provides
    fun provideProfileMapper(): ProfileDataToModelMapper {
        return ProfileDataToModelMapper()
    }

    @Provides
    fun providePresenceMapper(): PresenceDataToDomainMapper {
        return PresenceDataToDomainMapper()
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
    interface BindsPeopleDataModule {
        @Binds
        fun provideProfileRepository(impl: PeopleRepositoryImpl): PeopleRepository
    }
}
