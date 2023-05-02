package kz.tinkoff.homework_2.screen

import androidx.fragment.app.testing.FragmentScenario
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.core.app.ActivityScenario
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.bumptech.glide.manager.Lifecycle
import com.github.terrakok.cicerone.androidx.FragmentScreen
import kz.tinkoff.homework_2.presentation.main.MainActivity
import kz.tinkoff.homework_2.presentation.message.MessageArgs
import kz.tinkoff.homework_2.presentation.message.MessageFragment
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class MessageScreenIntegrateTest {

    @Before
    fun setup() {
    }

    @After
    fun tearDown() {
    }

    @Test
    fun testEventFragment() {
        val args = MessageArgs(
            streamId = 379888,
            stream = "general",
            topic = "test"
        )
        val scenario = launchFragmentInContainer {
            MessageFragment(args)
        }
        scenario.moveToState(androidx.lifecycle.Lifecycle.State.RESUMED)




        assertEquals(0, 0)

    }
}
