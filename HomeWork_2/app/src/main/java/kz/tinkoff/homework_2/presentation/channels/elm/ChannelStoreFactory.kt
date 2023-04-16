package kz.tinkoff.homework_2.presentation.channels.elm

import com.github.terrakok.cicerone.Router
import vivid.money.elmslie.coroutines.ElmStoreCompat

class ChannelStoreFactory(
    private val actor: ChannelActor,
    private val router: Router,
) {
    private val store by lazy {
        ElmStoreCompat(
            initialState = ChannelState.Loading,
            reducer = ChannelReducer(
                router = router
            ),
            actor = actor
        )
    }

    fun provide() = store
}
