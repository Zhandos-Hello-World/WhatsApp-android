package kz.tinkoff.homework_2.presentation.streams.elm

import javax.inject.Inject
import vivid.money.elmslie.coroutines.ElmStoreCompat

class StreamStoreFactory @Inject constructor(
    private val actor: StreamActor,
) {
    private val store by lazy {
        ElmStoreCompat(
            initialState = StreamState.Loading,
            reducer = StreamReducer(),
            actor = actor
        )
    }

    fun provide() = store
}
