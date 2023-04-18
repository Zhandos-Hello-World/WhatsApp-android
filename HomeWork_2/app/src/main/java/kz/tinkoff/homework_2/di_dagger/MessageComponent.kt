package kz.tinkoff.homework_2.di_dagger

import dagger.Component
import kz.tinkoff.homework_2.presentation.message.MessageFragment
import kz.tinkoff.homework_2.presentation.message.elm.MessageStoreFactory

@Component(modules = [MessageModule::class])
interface MessageComponent {

    fun getMessageStore(): MessageStoreFactory

    fun inject(fragment: MessageFragment)
}
