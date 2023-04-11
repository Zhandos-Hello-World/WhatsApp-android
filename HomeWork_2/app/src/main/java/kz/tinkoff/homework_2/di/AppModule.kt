package kz.tinkoff.homework_2.di

import kz.tinkoff.homework_2.presentation.mapper.MessageDvoMapper
import kz.tinkoff.homework_2.presentation.mapper.PersonDvoMapper
import kz.tinkoff.homework_2.presentation.mapper.ProfileDvoMapper
import kz.tinkoff.homework_2.presentation.mapper.StreamDvoMapper
import org.koin.dsl.module

val appModule = module {


    factory { MessageDvoMapper() }
    factory { PersonDvoMapper() }
    factory { StreamDvoMapper() }
    factory { ProfileDvoMapper() }

}