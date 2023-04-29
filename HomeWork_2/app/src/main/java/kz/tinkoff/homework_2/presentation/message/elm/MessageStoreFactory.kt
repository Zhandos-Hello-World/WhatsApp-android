package kz.tinkoff.homework_2.presentation.message.elm

import vivid.money.elmslie.coroutines.ElmStoreCompat

class MessageStoreFactory(private val actor: MessageActor) {
    private val store by lazy {
        ElmStoreCompat(
            initialState = MessageState.Loading,
            reducer = MessageReducer(),
            actor = actor
        )
    }

    fun provide() = store
}
