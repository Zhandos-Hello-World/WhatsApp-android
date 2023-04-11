package kz.tinkoff.homework_2.presentation.channels.elm

import vivid.money.elmslie.coroutines.ElmStoreCompat

class ChannelStoreFactory(private val actor: ChannelActor) {
    private val store by lazy {
        ElmStoreCompat(
            initialState = ChannelState(),
            reducer = ChannelReducer(),
            actor = actor
        )
    }

    fun provide() = store
}
