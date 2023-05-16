package kz.tinkoff.homework_2.presentation.main

import android.graphics.Rect
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.github.terrakok.cicerone.NavigatorHolder
import com.github.terrakok.cicerone.Router
import javax.inject.Inject
import kz.tinkoff.coreui.BottomBarController
import kz.tinkoff.homework_2.R
import kz.tinkoff.homework_2.databinding.ActivityMainBinding
import kz.tinkoff.homework_2.getAppComponent
import kz.tinkoff.homework_2.navigation.DefaultNavigatorDelegate
import kz.tinkoff.homework_2.navigation.NavigateDelegate
import kz.tinkoff.homework_2.navigation.Screens


class MainActivity : AppCompatActivity(R.layout.activity_main),
    BottomBarController,
    NavigateDelegate by DefaultNavigatorDelegate() {
    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!

    private var isShowBottomNavigationByFragment: Boolean = true

    @Inject
    lateinit var router: Router

    @Inject
    lateinit var navigateHolder: NavigatorHolder


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getAppComponent().inject(this)
        registerNavigatorDelegate(
            this,
            R.id.navHostFragment_2,
            navigateHolder
        )

        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.bottomNav.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.channels_item -> router.replaceScreen(Screens.StreamsScreen())
                R.id.people_item -> router.replaceScreen(Screens.PeopleScreen())
                R.id.profile_item -> router.replaceScreen(Screens.ProfileScreen())
            }
            savedInstanceState?.putInt(NAVIGATION_SELECTED_ARGS, item.itemId)
            true
        }

        binding.bottomNav.selectedItemId =
            savedInstanceState?.getInt(NAVIGATION_SELECTED_ARGS) ?: R.id.channels_item

        keyboardBottomNavHandle()
    }

    private fun keyboardBottomNavHandle() {
        val activityRootView = binding.root
        activityRootView.viewTreeObserver
            .addOnGlobalLayoutListener {
                val r = Rect()
                activityRootView.getWindowVisibleDisplayFrame(r)
                val screenHeight: Int = activityRootView.rootView.height
                val keyboardHeight: Int = screenHeight - r.bottom
                val isKeyboardShown = keyboardHeight > screenHeight * 0.15

                showBottomNavigationViewWhenKeyboardAppeared(!isKeyboardShown)
            }
    }


    override fun onResume() {
        super.onResume()
        onResumeFragmentsNavigator()
    }

    override fun onPause() {
        super.onPause()
        onPauseFragmentsNavigator()
    }

    override fun showBottomNavigationView(show: Boolean) {
        binding.bottomNav.isVisible = show
        isShowBottomNavigationByFragment = show
    }

    override fun showBottomNavigationViewWhenKeyboardAppeared(show: Boolean) {
        if (isShowBottomNavigationByFragment) {
            binding.bottomNav.isVisible = show
        }
    }

    companion object {
        const val NAVIGATION_SELECTED_ARGS = "NAVIGATION_SELECTED_ARGS"
    }

}
