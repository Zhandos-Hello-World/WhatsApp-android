package kz.tinkoff.homework_2.di_dagger.stream

import dagger.BindsInstance
import dagger.Component
import kz.tinkoff.homework_2.di_dagger.application.ApplicationComponent
import kz.tinkoff.homework_2.di_dagger.stream.modules.StreamDataModule
import kz.tinkoff.homework_2.di_dagger.stream.modules.StreamNetworkModule
import kz.tinkoff.homework_2.presentation.create_stream.CreateStreamFragment
import kz.tinkoff.homework_2.presentation.streams.StreamsContainerFragment
import kz.tinkoff.homework_2.presentation.streams.list.StreamsListFragment

@StreamScope
@Component(
    modules = [StreamDataModule::class, StreamNetworkModule::class],
    dependencies = [ApplicationComponent::class]
)
interface StreamComponent {

    fun inject(fragment: StreamsListFragment)
    fun inject(fragment: CreateStreamFragment)
    fun inject(fragment: StreamsContainerFragment)


    @Component.Builder
    interface Builder {
        fun appComponent(component: ApplicationComponent): Builder

        fun build(): StreamComponent
    }

    companion object {
        fun builder(): Builder {
            return DaggerStreamComponent.builder()
        }
    }
}
