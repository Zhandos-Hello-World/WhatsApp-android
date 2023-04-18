package kz.tinkoff.homework_2.navigation

import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.DefaultLifecycleObserver
import com.github.terrakok.cicerone.Navigator
import com.github.terrakok.cicerone.NavigatorHolder
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

interface NavigateDelegate {
    fun onResumeFragmentsNavigator()
    fun registerNavigatorDelegate(activity: FragmentActivity, containerId: Int)
    fun onPauseFragmentsNavigator()
}

class DefaultNavigatorDelegate: NavigateDelegate, KoinComponent, DefaultLifecycleObserver {
    private val navigatorHolder: NavigatorHolder by inject()
    private lateinit var navigator: Navigator

    override fun registerNavigatorDelegate(activity: FragmentActivity, containerId: Int) {
        activity.lifecycle.addObserver(this)
        navigator = MyNavigator(activity, containerId)
    }

    override fun onResumeFragmentsNavigator() {
        navigatorHolder.setNavigator(navigator)
    }

    override fun onPauseFragmentsNavigator() {
        navigatorHolder.removeNavigator()
    }


}
