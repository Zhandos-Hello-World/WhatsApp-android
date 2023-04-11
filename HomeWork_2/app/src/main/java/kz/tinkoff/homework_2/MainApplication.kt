package kz.tinkoff.homework_2

import android.app.Application
import kz.tinkoff.homework_2.di.appModule
import kz.tinkoff.homework_2.di.dataModule
import kz.tinkoff.homework_2.di.navigationModule
import kz.tinkoff.homework_2.di.networkModule
import kz.tinkoff.homework_2.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class MainApplication : Application() {

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

    }
}