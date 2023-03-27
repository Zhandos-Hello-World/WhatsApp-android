package kz.tinkoff.homework_2.presentation.main

import android.graphics.Rect
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.github.terrakok.cicerone.Router
import kz.tinkoff.coreui.BottomBarController
import kz.tinkoff.homework_2.R
import kz.tinkoff.homework_2.databinding.ActivityMainBinding
import kz.tinkoff.homework_2.navigation.DefaultNavigatorDelegate
import kz.tinkoff.homework_2.navigation.NavigateDelegate
import kz.tinkoff.homework_2.navigation.Screens
import org.koin.android.ext.android.inject


class MainActivity : AppCompatActivity(R.layout.activity_main),
    BottomBarController,
    NavigateDelegate by DefaultNavigatorDelegate() {
    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!

    private val router: Router by inject()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        registerNavigatorDelegate(this, R.id.navHostFragment_2)

        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.bottomNav.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.channels_item -> router.replaceScreen(Screens.ChannelsScreen())
                R.id.people_item -> router.replaceScreen(Screens.PeopleScreen())
                R.id.profile_item -> router.replaceScreen(Screens.ProfileScreen())
            }
            true
        }
        binding.bottomNav.selectedItemId = R.id.channels_item
        keyboardBottomNavHandle()
    }

    private fun keyboardBottomNavHandle() {
        val activityRootView = binding.root
        activityRootView.viewTreeObserver
            .addOnGlobalLayoutListener { // Check if the keyboard is shown
                val r = Rect()
                activityRootView.getWindowVisibleDisplayFrame(r)
                val screenHeight: Int = activityRootView.rootView.height
                val keyboardHeight: Int = screenHeight - r.bottom
                val isKeyboardShown = keyboardHeight > screenHeight * 0.15

                showBottomNavigationView(!isKeyboardShown)
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
    }

}