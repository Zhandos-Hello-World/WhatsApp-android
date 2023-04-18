package kz.tinkoff.homework_2.di_dagger

import com.github.terrakok.cicerone.NavigatorHolder
import com.github.terrakok.cicerone.Router
import dagger.Component

@Component(modules = [NavigationModule::class])
interface NavigationComponent {

    fun getRouter(): Router

    fun getNavigationHolder(): NavigatorHolder
}
