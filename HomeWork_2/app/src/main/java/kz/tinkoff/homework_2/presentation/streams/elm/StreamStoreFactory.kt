package kz.tinkoff.homework_2.presentation.streams.elm

import com.github.terrakok.cicerone.Router
import javax.inject.Inject
import vivid.money.elmslie.coroutines.ElmStoreCompat

class StreamStoreFactory @Inject constructor(
    private val actor: StreamActor,
    private val router: Router,
) {
    private val store by lazy {
        ElmStoreCompat(
            initialState = StreamState.Loading,
            reducer = StreamReducer(
                router = router
            ),
            actor = actor
        )
    }

    fun provide() = store
}
