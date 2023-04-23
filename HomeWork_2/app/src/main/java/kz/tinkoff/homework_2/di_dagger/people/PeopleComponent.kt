package kz.tinkoff.homework_2.di_dagger.people

import dagger.Component
import kz.tinkoff.homework_2.di_dagger.application.ApplicationComponent
import kz.tinkoff.homework_2.presentation.people.PeopleFragment
import kz.tinkoff.homework_2.presentation.profile.ProfileFragment

@PeopleScope
@Component(
    modules = [PeopleModule::class],
    dependencies = [ApplicationComponent::class]
)
interface PeopleComponent {

    fun inject(fragment: PeopleFragment)
    fun inject(profileFragment: ProfileFragment)

    @Component.Factory
    interface Factory {
        fun create(
            module: PeopleModule,
            appComponent: ApplicationComponent,
        ): PeopleComponent
    }

}
