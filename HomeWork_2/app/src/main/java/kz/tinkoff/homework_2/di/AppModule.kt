package kz.tinkoff.homework_2.di

import kz.tinkoff.homework_2.mapper.ChannelDvoMapper
import kz.tinkoff.homework_2.mapper.MessageDvoMapper
import kz.tinkoff.homework_2.mapper.PersonDvoMapper
import kz.tinkoff.homework_2.presentation.channels.list.ChannelsListViewModel
import kz.tinkoff.homework_2.presentation.message.MessageViewModel
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
        ChannelsListViewModel(
            repository = get(),
            mapper = get(),
            router = get()
        )
    }

    factory { MessageDvoMapper() }
    factory { PersonDvoMapper() }
    factory { ChannelDvoMapper() }

}