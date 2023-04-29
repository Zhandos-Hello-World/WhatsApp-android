package kz.tinkoff.homework_2.di_dagger.stream

import dagger.BindsInstance
import dagger.Component
import kz.tinkoff.homework_2.di_dagger.application.ApplicationComponent
import kz.tinkoff.homework_2.di_dagger.stream.modules.StreamDataModule
import kz.tinkoff.homework_2.di_dagger.stream.modules.StreamNetworkModule
import kz.tinkoff.homework_2.presentation.channels.list.ChannelsListFragment

@StreamScope
@Component(
    modules = [StreamDataModule::class, StreamNetworkModule::class],
    dependencies = [ApplicationComponent::class]
)
interface StreamComponent {

    fun inject(fragment: ChannelsListFragment)


    @Component.Builder
    interface Builder {

        @BindsInstance
        fun streamNetworkModule(module: StreamNetworkModule): Builder

        @BindsInstance
        fun streamDataModule(module: StreamDataModule): Builder

        fun appComponent(component: ApplicationComponent): Builder

        fun build(): StreamComponent

    }

    companion object {
        fun builder(): Builder {
            return DaggerStreamComponent.builder()
        }
    }
}
