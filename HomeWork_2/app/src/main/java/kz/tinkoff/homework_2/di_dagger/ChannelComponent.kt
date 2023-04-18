package kz.tinkoff.homework_2.di_dagger

import dagger.Component
import kz.tinkoff.homework_2.presentation.channels.elm.ChannelStoreFactory

@Component(modules = [ChannelModule::class])
interface ChannelComponent {

    fun getChannelStore(): ChannelStoreFactory
}
