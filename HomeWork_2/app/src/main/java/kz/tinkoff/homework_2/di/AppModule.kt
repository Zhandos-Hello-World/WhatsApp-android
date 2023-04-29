package kz.tinkoff.homework_2.di

import kz.tinkoff.homework_2.presentation.mapper.MessageDelegateItemMapper
import kz.tinkoff.homework_2.presentation.mapper.MessageDvoMapper
import kz.tinkoff.homework_2.presentation.mapper.PersonDelegateItemMapper
import kz.tinkoff.homework_2.presentation.mapper.PersonDvoMapper
import kz.tinkoff.homework_2.presentation.mapper.ProfileDvoMapper
import kz.tinkoff.homework_2.presentation.mapper.ReactionViewItemMapper
import kz.tinkoff.homework_2.presentation.mapper.StreamDvoMapper
import org.koin.dsl.module

val appModule = module {

    factory { ReactionViewItemMapper() }
    factory { PersonDelegateItemMapper() }
    factory { ReactionViewItemMapper() }
    factory { MessageDvoMapper(reactionViewItemMapper = get()) }
    factory { MessageDelegateItemMapper(dvoMapper = get()) }
    factory { MessageDvoMapper(reactionViewItemMapper = get()) }
    factory { PersonDvoMapper(delegateItemMapper = get()) }
    factory { StreamDvoMapper() }
    factory { ProfileDvoMapper() }

}
