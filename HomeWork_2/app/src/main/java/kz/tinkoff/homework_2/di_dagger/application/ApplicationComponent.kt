package kz.tinkoff.homework_2.di_dagger.application

import android.content.Context
import com.github.terrakok.cicerone.NavigatorHolder
import com.github.terrakok.cicerone.Router
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton
import kz.tinkoff.homework_2.data.dao.MessageDao
import kz.tinkoff.homework_2.presentation.main.MainActivity
import retrofit2.Retrofit

@Component(modules = [NetworkModule::class, NavigationModule::class, DatabaseModule::class])
@Singleton
interface ApplicationComponent {

    fun getRouter(): Router

    fun navigationHolder(): NavigatorHolder

    fun inject(activity: MainActivity)

    fun provideRetrofit(): Retrofit

    fun provideDao(): MessageDao

    @Component.Factory
    interface Factory {
        fun create(
            @BindsInstance
            context: Context,
        ): ApplicationComponent
    }
}
