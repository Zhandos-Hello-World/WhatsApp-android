package kz.tinkoff.homework_2.di_dagger.people

import dagger.BindsInstance
import dagger.Component
import kz.tinkoff.homework_2.di_dagger.application.ApplicationComponent
import kz.tinkoff.homework_2.di_dagger.people.modules.PeopleDataModule
import kz.tinkoff.homework_2.di_dagger.people.modules.PeopleNetworkModule
import kz.tinkoff.homework_2.presentation.people.PeopleFragment
import kz.tinkoff.homework_2.presentation.profile.ProfileFragment

@PeopleScope
@Component(
    modules = [PeopleDataModule::class, PeopleNetworkModule::class],
    dependencies = [ApplicationComponent::class]
)
interface PeopleComponent {

    fun inject(fragment: PeopleFragment)
    fun inject(profileFragment: ProfileFragment)


    @Component.Builder
    interface Builder {
        fun appComponent(component: ApplicationComponent): Builder

        fun build(): PeopleComponent
    }

    companion object {
        fun builder(): Builder {
            return DaggerPeopleComponent.builder()
        }
    }
}
