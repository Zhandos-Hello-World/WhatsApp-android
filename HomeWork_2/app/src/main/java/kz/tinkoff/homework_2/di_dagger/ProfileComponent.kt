package kz.tinkoff.homework_2.di_dagger

import dagger.Component
import kz.tinkoff.homework_2.presentation.profile.ProfileFragment
import kz.tinkoff.homework_2.presentation.profile.elm.ProfileStoreFactory

@Component(modules = [ProfileModule::class])
interface ProfileComponent {

    fun inject(fragment: ProfileFragment)

    fun getProfileStore(): ProfileStoreFactory
}
