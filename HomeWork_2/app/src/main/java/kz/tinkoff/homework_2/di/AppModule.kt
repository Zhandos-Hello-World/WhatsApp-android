package kz.tinkoff.homework_2.di

import kz.tinkoff.homework_2.presentation.channels.list.ChannelsListViewModel
import kz.tinkoff.homework_2.presentation.mapper.StreamDvoMapper
import kz.tinkoff.homework_2.presentation.mapper.MessageDvoMapper
import kz.tinkoff.homework_2.presentation.mapper.PersonDvoMapper
import kz.tinkoff.homework_2.presentation.mapper.ProfileDvoMapper
import kz.tinkoff.homework_2.presentation.message.MessageViewModel
import kz.tinkoff.homework_2.presentation.profile.ProfileViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {

    viewModel {
        MessageViewModel(
            messageDvoMapper = get(),
            router = get(),
            repository = get()
        )
    }

    viewModel {
        ProfileViewModel(
            repository = get(),
            mapper = get()
        )
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
    factory { StreamDvoMapper() }
    factory { ProfileDvoMapper() }

}