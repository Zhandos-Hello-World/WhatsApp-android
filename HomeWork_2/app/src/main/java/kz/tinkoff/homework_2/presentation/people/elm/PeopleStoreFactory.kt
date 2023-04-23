package kz.tinkoff.homework_2.presentation.people.elm

import javax.inject.Inject
import vivid.money.elmslie.coroutines.ElmStoreCompat

class PeopleStoreFactory @Inject constructor(private val actor: PeopleActor) {
    private val store by lazy {
        ElmStoreCompat(
            initialState = PeopleState.Loading,
            reducer = PeopleReducer(),
            actor = actor
        )
    }

    fun provide() = store
}
