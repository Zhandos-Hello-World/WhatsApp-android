package kz.tinkoff.homework_2

import android.app.Application
import com.github.terrakok.cicerone.Router
import kz.tinkoff.homework_2.di.appModule
import kz.tinkoff.homework_2.di.dataModule
import kz.tinkoff.homework_2.di.navigationModule
import kz.tinkoff.homework_2.di.networkModule
import kz.tinkoff.homework_2.di.viewModelModule
import kz.tinkoff.homework_2.di_dagger.ChannelComponent
import kz.tinkoff.homework_2.di_dagger.ChannelModule
import kz.tinkoff.homework_2.di_dagger.DaggerChannelComponent
import kz.tinkoff.homework_2.di_dagger.DaggerMessageComponent
import kz.tinkoff.homework_2.di_dagger.DaggerNavigationComponent
import kz.tinkoff.homework_2.di_dagger.DaggerPeopleComponent
import kz.tinkoff.homework_2.di_dagger.DaggerProfileComponent
import kz.tinkoff.homework_2.di_dagger.MessageComponent
import kz.tinkoff.homework_2.di_dagger.MessageModule
import kz.tinkoff.homework_2.di_dagger.NavigationComponent
import kz.tinkoff.homework_2.di_dagger.NavigationModule
import kz.tinkoff.homework_2.di_dagger.PeopleComponent
import kz.tinkoff.homework_2.di_dagger.PeopleModule
import kz.tinkoff.homework_2.di_dagger.ProfileComponent
import kz.tinkoff.homework_2.di_dagger.ProfileModule
import org.koin.android.ext.android.inject
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class MainApplication : Application() {

    lateinit var messageComponent: MessageComponent
    lateinit var profileComponent: ProfileComponent
    lateinit var peopleComponent: PeopleComponent
    lateinit var channelComponent: ChannelComponent
    lateinit var navigationComponent: NavigationComponent
    private val router: Router by inject()

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(applicationContext)
            androidLogger()
            modules(
                appModule,
                navigationModule,
                networkModule,
                dataModule,
                viewModelModule
            )
        }

        navigationComponent = DaggerNavigationComponent.builder().navigationModule(
            NavigationModule()
        ).build()

        messageComponent = DaggerMessageComponent.builder().messageModule(
            MessageModule(applicationContext, router)
        ).build()

        profileComponent = DaggerProfileComponent.builder().profileModule(
            ProfileModule(applicationContext)
        ).build()

        peopleComponent = DaggerPeopleComponent.builder().peopleModule(
            PeopleModule(applicationContext)
        ).build()

        channelComponent = DaggerChannelComponent.builder().channelModule(
            ChannelModule(applicationContext, router)
        ).build()

    }
}
