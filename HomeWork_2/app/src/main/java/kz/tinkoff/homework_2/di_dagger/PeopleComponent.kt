package kz.tinkoff.homework_2.di_dagger

import dagger.Component
import kz.tinkoff.homework_2.presentation.people.PeopleFragment
import kz.tinkoff.homework_2.presentation.people.elm.PeopleStoreFactory

@Component(modules = [PeopleModule::class])
interface PeopleComponent {

    fun inject(fragment: PeopleFragment)

    fun getPeopleStore(): PeopleStoreFactory

}
