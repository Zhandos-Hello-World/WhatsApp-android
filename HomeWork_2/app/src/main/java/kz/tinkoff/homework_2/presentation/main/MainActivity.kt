package kz.tinkoff.homework_2.presentation.main

import android.content.Context
import android.graphics.Rect
import android.net.ConnectivityManager
import android.net.NetworkRequest
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.github.terrakok.cicerone.NavigatorHolder
import com.github.terrakok.cicerone.Router
import com.google.android.material.snackbar.Snackbar
import javax.inject.Inject
import kz.tinkoff.coreui.BottomBarController
import kz.tinkoff.homework_2.R
import kz.tinkoff.homework_2.data.network.NetworkStatusListener
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

    private var connectivityManager: ConnectivityManager? = null
    private var networkStatusListener: NetworkStatusListener? = null


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

        connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        networkStatusListener = NetworkStatusListener { connected ->
            Snackbar.make(
                binding.root,
                resources.getString(if (connected) R.string.connected else R.string.disconnected),
                Snackbar.LENGTH_SHORT
            ).show()
        }
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
        val networkRequest = NetworkRequest.Builder().build()
        networkStatusListener?.let { networkStatusListener ->
            connectivityManager?.registerNetworkCallback(networkRequest, networkStatusListener)
        }
    }

    override fun onPause() {
        super.onPause()
        onPauseFragmentsNavigator()
        networkStatusListener?.let { networkStatusListener ->
            connectivityManager?.unregisterNetworkCallback(networkStatusListener)
        }
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
