package kz.tinkoff.homework_2.di_dagger.stream

import dagger.Component
import kz.tinkoff.homework_2.di_dagger.application.ApplicationComponent
import kz.tinkoff.homework_2.presentation.channels.list.ChannelsListFragment

@StreamScope
@Component(modules = [StreamModule::class], dependencies = [ApplicationComponent::class])
interface StreamComponent {

    fun inject(fragment: ChannelsListFragment)


    @Component.Factory
    interface Factory {
        fun create(appComponent: ApplicationComponent, module: StreamModule): StreamComponent
    }
}
