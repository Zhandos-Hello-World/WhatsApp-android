package kz.tinkoff.homework_2.di

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kz.tinkoff.homework_2.presentation.channels.elm.ChannelActor
import kz.tinkoff.homework_2.presentation.channels.elm.ChannelStoreFactory
import kz.tinkoff.homework_2.presentation.message.elm.MessageActor
import kz.tinkoff.homework_2.presentation.message.elm.MessageStoreFactory
import kz.tinkoff.homework_2.presentation.people.elm.PeopleActor
import kz.tinkoff.homework_2.presentation.people.elm.PeopleStoreFactory
import kz.tinkoff.homework_2.presentation.profile.elm.ProfileActor
import kz.tinkoff.homework_2.presentation.profile.elm.ProfileStoreFactory
import org.koin.dsl.module

val viewModelModule = module {

    single {
        ProfileActor(
            repository = get(),
            dvoMapper = get(),
        )
    }

    single {
        PeopleActor(
            repository = get(),
            dvoMapper = get(),
        )
    }

    single {
        ChannelActor(
            repository = get(),
            dvoMapper = get(),
            coroutineScope = CoroutineScope(Dispatchers.IO)
        )
    }

    single {
        MessageActor(
            repository = get(),
            router = get(),
            delegateItemMapper = get()
        )
    }


    single {
        ProfileStoreFactory(actor = get())
    }

    single {
        PeopleStoreFactory(actor = get())
    }

    single {
        ChannelStoreFactory(actor = get(), router = get())
    }

    single {
        MessageStoreFactory(
            actor = get()
        )
    }

}
