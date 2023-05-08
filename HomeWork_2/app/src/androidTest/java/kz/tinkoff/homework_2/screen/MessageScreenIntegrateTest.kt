package kz.tinkoff.homework_2.screen

import android.content.Context
import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.ApplicationProvider
import com.github.terrakok.cicerone.androidx.FragmentScreen
import com.kaspersky.kaspresso.testcases.api.testcase.TestCase
import kz.tinkoff.core.R
import kz.tinkoff.homework_2.di_dagger.application.Constants
import kz.tinkoff.homework_2.presentation.main.MainActivity
import kz.tinkoff.homework_2.presentation.message.MessageArgs
import kz.tinkoff.homework_2.presentation.message.MessageFragment
import kz.tinkoff.homework_2.util.MockRequestDispatcher
import kz.tinkoff.homework_2.util.loadFromAssets
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test


class MessageScreenIntegrateTest : TestCase() {

    @get:Rule
    val mockServer = MockWebServer()

    private lateinit var context: Context
    private lateinit var args: MessageArgs

    @Before
    fun setUp() {
        changeMessageResponse(MESSAGE_LIST_RESPONSE_NAME)
        Constants.BASE_URL = mockServer.url("/").toString()
        context = ApplicationProvider.getApplicationContext()

        args = MessageArgs(
            streamId = 379888,
            stream = "general",
            topic = "test"
        )
    }

    @Test
    fun checkToolbar() = run {
        val scenario = ActivityScenario.launch(MainActivity::class.java)
        scenario.onActivity { activity ->
            activity.router.replaceScreen(FragmentScreen { MessageFragment(args) })
        }

        val messageScreen = MessageScreen()
        step("Checking toolbar") {
            messageScreen.toolbarText.hasText(args.stream)
        }
    }

    @Test
    fun checkSubToolbar() = run {
        val scenario = ActivityScenario.launch(MainActivity::class.java)
        scenario.onActivity { activity ->
            activity.router.replaceScreen(FragmentScreen { MessageFragment(args) })
        }
        val messageScreen = MessageScreen()
        step("Checking subToolbar") {
            messageScreen.subToolbarText.hasText(
                context.getString(R.string.topic) + args.topic
            )
        }
    }

    @Test
    fun checkEmptyList() = run {
        val scenario = ActivityScenario.launch(MainActivity::class.java)
        scenario.onActivity { activity ->
            activity.router.replaceScreen(FragmentScreen { MessageFragment(args) })
        }
        val messageScreen = MessageScreen()

        step("Message recycler view is not displayed and it is empty") {
            messageScreen.messageList.isNotDisplayed()
            messageScreen.messageList.hasSize(0)
        }
    }

    @Test
    fun checkListHasSize() = run {
        val scenario = ActivityScenario.launch(MainActivity::class.java)
        scenario.onActivity { activity ->
            activity.router.replaceScreen(FragmentScreen { MessageFragment(args) })
        }
        val messageScreen = MessageScreen()

        step("Message recycler view has size and displayed") {
            messageScreen.messageList.isDisplayed()
            messageScreen.messageList.hasSize(49)
        }
    }

    @Test
    fun checkListWhenError() = run {
        changeMessageResponse(MESSAGE_LIST_RESPONSE_NAME, 400)
        val scenario = ActivityScenario.launch(MainActivity::class.java)
        scenario.onActivity { activity ->
            activity.router.replaceScreen(FragmentScreen { MessageFragment(args) })
        }
        val messageScreen = MessageScreen()

        step("Check when server is not response") {
            messageScreen.errorState.isDisplayed()
        }
        changeMessageResponse(MESSAGE_LIST_RESPONSE_NAME)
    }

    @After
    fun tearDown() {
        mockServer.shutdown()
    }


    private fun changeMessageResponse(response: String, responseCode: Int = 200) {
        mockServer.dispatcher = MockRequestDispatcher().apply {
            returnsForPath(PATH) {
                setBody(loadFromAssets(response))
                setResponseCode(responseCode)
            }
        }
    }

    private companion object {
        const val PATH =
            "/api/v1/messages?anchor=newest&narrow=%5B%7B%22operator%22%3A%20%22stream%22%2C%20%22operand%22%3A%20%22general%22%7D%5D&num_before=1000&num_after=1000"
        const val MESSAGE_LIST_RESPONSE_NAME = "message_list.json"
    }
}
