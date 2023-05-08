package kz.tinkoff.homework_2

import android.app.Application
import android.content.Context
import kz.tinkoff.homework_2.di_dagger.application.ApplicationComponent
import kz.tinkoff.homework_2.di_dagger.application.DaggerApplicationComponent

class MainApplication : Application() {

    lateinit var appComponent: ApplicationComponent

    override fun onCreate() {
        super.onCreate()

        appComponent = DaggerApplicationComponent.factory().create(applicationContext)
    }
}

fun Context.getAppComponent(): ApplicationComponent =
    (this.applicationContext as MainApplication).appComponent
