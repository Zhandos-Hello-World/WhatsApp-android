package kz.tinkoff.homework_2.di

import kz.tinkoff.homework_2.data.datasource.DefaultStreamNetworkDataSource
import kz.tinkoff.homework_2.data.datasource.DefaultMessageNetworkDataSource
import kz.tinkoff.homework_2.data.datasource.DefaultPeopleNetworkDataSource
import kz.tinkoff.homework_2.data.mappers.MessageDtoMapper
import kz.tinkoff.homework_2.data.mappers.MessageMapper
import kz.tinkoff.homework_2.data.mappers.PersonMapper
import kz.tinkoff.homework_2.data.mappers.PresenceMapper
import kz.tinkoff.homework_2.data.mappers.ProfileMapper
import kz.tinkoff.homework_2.data.mappers.ReactionDtoMapper
import kz.tinkoff.homework_2.data.mappers.StreamMapper
import kz.tinkoff.homework_2.data.mappers.TopicMapper
import kz.tinkoff.homework_2.data.repository.RepoStreamImpl
import kz.tinkoff.homework_2.data.repository.RepoMessageImpl
import kz.tinkoff.homework_2.data.repository.RepoPeopleImpl
import kz.tinkoff.homework_2.domain.datasource.ChannelRemoteDataSource
import kz.tinkoff.homework_2.domain.datasource.MessageRemoteDataSource
import kz.tinkoff.homework_2.domain.datasource.PeopleRemoteDataSource
import kz.tinkoff.homework_2.domain.repository.ChannelRepository
import kz.tinkoff.homework_2.domain.repository.MessageRepository
import kz.tinkoff.homework_2.domain.repository.PeopleRepository
import org.koin.dsl.module

val dataModule = module {

    single<PeopleRemoteDataSource> {
        DefaultPeopleNetworkDataSource(
            apiService = get(),
        )
    }

    single<ChannelRemoteDataSource> {
        DefaultStreamNetworkDataSource(
            apiService = get(),
        )
    }

    single<MessageRemoteDataSource> {
        DefaultMessageNetworkDataSource(
            apiService = get()
        )
    }

    single<ChannelRepository> {
        RepoStreamImpl(dataSource = get(), streamMapper = get(), topicMapper = get())
    }

    single<PeopleRepository> {
        RepoPeopleImpl(dataSource = get(),
            peopleMapper = get(),
            profileMapper = get(),
            presenceMapper = get()
        )
    }

    single<MessageRepository> {
        RepoMessageImpl(
            dataSource = get(),
            mapper = get(),
            dtoMessageMapper = get(),
            dtoReactionMapper = get()
        )
    }

    factory { ProfileMapper() }
    factory { PersonMapper() }
    factory { StreamMapper() }
    factory { PresenceMapper() }
    factory { TopicMapper() }
    factory { MessageMapper() }
    factory { MessageDtoMapper() }
    factory { ReactionDtoMapper() }
}