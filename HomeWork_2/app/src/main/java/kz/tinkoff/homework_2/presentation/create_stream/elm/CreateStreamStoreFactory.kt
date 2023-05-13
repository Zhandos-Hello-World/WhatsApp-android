package kz.tinkoff.homework_2.presentation.create_stream.elm

import com.github.terrakok.cicerone.Router
import javax.inject.Inject
import vivid.money.elmslie.coroutines.ElmStoreCompat

class CreateStreamStoreFactory @Inject constructor(
    private val actor: CreateStreamActor,
    private val router: Router,
) {
    private val store by lazy {
        ElmStoreCompat(
            initialState = CreateStreamState.NotInit,
            reducer = CreateStreamReducer(router),
            actor = actor
        )
    }

    fun provide() = store
}
