package kz.tinkoff.homework_2.di

import kz.tinkoff.homework_2.data.datasource.DefaultChannelsNetworkDataSource
import kz.tinkoff.homework_2.data.datasource.DefaultPeopleNetworkDataSource
import kz.tinkoff.homework_2.data.mappers.ChannelMapper
import kz.tinkoff.homework_2.data.mappers.PersonMapper
import kz.tinkoff.homework_2.data.network.ApiService
import kz.tinkoff.homework_2.data.network.FakeApiService
import kz.tinkoff.homework_2.data.network.FakeCommonFactory
import kz.tinkoff.homework_2.data.repository.DefaultChannelRepository
import kz.tinkoff.homework_2.data.repository.DefaultPeopleRepository
import kz.tinkoff.homework_2.domain.datasource.ChannelRemoteDataSource
import kz.tinkoff.homework_2.domain.datasource.PeopleRemoteDataSource
import kz.tinkoff.homework_2.domain.repository.ChannelRepository
import kz.tinkoff.homework_2.domain.repository.PeopleRepository
import org.koin.dsl.module

val dataModule = module {

    single<ApiService> { FakeApiService(factory = get()) }

    single<PeopleRemoteDataSource> {
        DefaultPeopleNetworkDataSource(
            apiService = get(),
        )
    }

    single<ChannelRemoteDataSource> {
        DefaultChannelsNetworkDataSource(
            apiService = get(),
        )
    }

    single<ChannelRepository> {
        DefaultChannelRepository(dataSource = get(), mapper = get())
    }

    single<PeopleRepository> {
        DefaultPeopleRepository(dataSource = get(), mapper = get())
    }

    factory { FakeCommonFactory() }
    factory { PersonMapper() }
    factory { ChannelMapper() }
}