package kz.tinkoff.homework_2.di

import kz.tinkoff.homework_2.mapper.ChannelDvoMapper
import kz.tinkoff.homework_2.mapper.MessageDvoMapper
import kz.tinkoff.homework_2.mapper.PersonDvoMapper
import kz.tinkoff.homework_2.presentation.channels.list.ChannelsFactory
import kz.tinkoff.homework_2.presentation.channels.list.ChannelsListViewModel
import kz.tinkoff.homework_2.presentation.channels.list.DefaultChannelListFactory
import kz.tinkoff.homework_2.presentation.message.MessageViewModel
import kz.tinkoff.homework_2.presentation.people.DefaultPeopleListFactory
import kz.tinkoff.homework_2.presentation.people.PeopleListFactory
import kz.tinkoff.homework_2.presentation.people.PeopleViewModel
import kz.tinkoff.homework_2.presentation.profile.ProfileViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {

    viewModel {
        MessageViewModel(
            messageDvoMapper = get(),
            router = get(),
        )
    }

    viewModel {
        ProfileViewModel()
    }

    viewModel {
        PeopleViewModel(
            factory = get(),
            personDvoMapper = get(),
        )
    }

    viewModel {
        ChannelsListViewModel(
            factory = get(),
            channelDvoMapper = get(),
            router = get(),
        )
    }

    factory { MessageDvoMapper() }
    factory { PersonDvoMapper() }
    factory { ChannelDvoMapper() }

    factory<PeopleListFactory> { DefaultPeopleListFactory() }
    factory<ChannelsFactory> { DefaultChannelListFactory() }
}