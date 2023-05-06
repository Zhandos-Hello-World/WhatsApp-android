package kz.tinkoff.homework_2.screen

import kz.tinkoff.coreui.R as coreUIR
import android.view.View
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.BoundedMatcher
import com.kaspersky.kaspresso.screens.KScreen
import io.github.kakaocup.kakao.common.assertions.BaseAssertions
import io.github.kakaocup.kakao.common.builders.ViewBuilder
import io.github.kakaocup.kakao.common.views.KBaseView
import io.github.kakaocup.kakao.common.views.KView
import io.github.kakaocup.kakao.recycler.KRecyclerItem
import io.github.kakaocup.kakao.recycler.KRecyclerView
import io.github.kakaocup.kakao.text.KTextView
import kz.tinkoff.coreui.custom.view.CustomSubToolbar
import kz.tinkoff.homework_2.R
import org.hamcrest.Description
import org.hamcrest.Matcher

class MessageScreen : KScreen<MessageScreen>() {
    override val layoutId: Int = R.layout.fragment_message
    override val viewClass: Class<*> = MessageScreen::class.java

    val messageList =
        KRecyclerView({ withId(R.id.message_recycler) }, { itemType { MessageItem(it) } })

    val toolbarText = KTextView(function = { withId(coreUIR.id.toolbar_text) })
    val subToolbarText = KSubToolbarText(function = { withId(R.id.sub_toolbar) })
    val errorState = KView(function = { withId(R.id.error_state) })

    class MessageItem(parent: Matcher<View>) : KRecyclerItem<MessageItem>(parent)

}


class KSubToolbarText(function: ViewBuilder.() -> Unit) : KBaseView<CustomSubToolbar>(function),
    KSubToolbarAssertions

interface KSubToolbarAssertions : BaseAssertions {

    fun hasText(text: String) {
        view.check(
            ViewAssertions.matches(
                CustomSubToolbarTextMatchers(text)
            )
        )
    }
}

class CustomSubToolbarTextMatchers(private val text: String) :
    BoundedMatcher<View, CustomSubToolbar>(CustomSubToolbar::class.java) {

    override fun describeTo(description: Description?) {
        description?.appendText("CustomSubToolbar text is not")
    }

    override fun matchesSafely(item: CustomSubToolbar?): Boolean {
        return item?.title.orEmpty() == text
    }
}
