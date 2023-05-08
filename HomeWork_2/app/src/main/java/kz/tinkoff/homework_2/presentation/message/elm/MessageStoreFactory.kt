package kz.tinkoff.homework_2.presentation.message.elm

import javax.inject.Inject
import vivid.money.elmslie.coroutines.ElmStoreCompat

class MessageStoreFactory @Inject constructor(private val actor: MessageActor) {
    private val store by lazy {
        ElmStoreCompat(
            initialState = MessageState.Loading,
            reducer = MessageReducer(),
            actor = actor
        )
    }

    fun provide() = store
}
