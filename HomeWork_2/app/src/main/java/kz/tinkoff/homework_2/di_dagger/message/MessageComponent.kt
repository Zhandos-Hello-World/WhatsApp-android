package kz.tinkoff.homework_2.di_dagger.message

import dagger.BindsInstance
import dagger.Component
import kz.tinkoff.homework_2.di_dagger.application.ApplicationComponent
import kz.tinkoff.homework_2.di_dagger.message.modules.MessageDataModule
import kz.tinkoff.homework_2.di_dagger.message.modules.MessageNetworkModule
import kz.tinkoff.homework_2.presentation.create_topic.CreateTopicFragment
import kz.tinkoff.homework_2.presentation.message.MessageFragment

@MessageScope
@Component(
    modules = [MessageDataModule::class, MessageNetworkModule::class],
    dependencies = [ApplicationComponent::class]
)
interface MessageComponent {

    fun inject(fragment: MessageFragment)
    fun inject(fragment: CreateTopicFragment)

    @Component.Builder
    interface Builder {

        fun appComponent(component: ApplicationComponent): Builder

        fun build(): MessageComponent

    }

    companion object {
        fun builder(): Builder {
            return DaggerMessageComponent.builder()
        }
    }
}
