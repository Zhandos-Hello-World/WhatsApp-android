package kz.tinkoff.homework_2.presentation.create_topic.elm

import javax.inject.Inject
import vivid.money.elmslie.coroutines.ElmStoreCompat

class CreateTopicStoreFactory @Inject constructor(
    val actor: CreateTopicActor,
) {
    private val store by lazy {
        ElmStoreCompat(
            initialState = CreateTopicState.NotInit,
            reducer = CreateTopicReducer(),
            actor = actor
        )
    }

    fun provide() = store
}
