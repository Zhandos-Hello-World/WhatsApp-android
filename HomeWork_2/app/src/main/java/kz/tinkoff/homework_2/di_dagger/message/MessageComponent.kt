package kz.tinkoff.homework_2.di_dagger.message

import dagger.Component
import kz.tinkoff.homework_2.di_dagger.application.ApplicationComponent
import kz.tinkoff.homework_2.presentation.message.MessageFragment

@MessageScope
@Component(modules = [MessageModule::class], dependencies = [ApplicationComponent::class])
interface MessageComponent {

    fun inject(fragment: MessageFragment)

    @Component.Factory
    interface Factory {
        fun create(
            module: MessageModule,
            appComponent: ApplicationComponent,
        ): MessageComponent
    }
}
