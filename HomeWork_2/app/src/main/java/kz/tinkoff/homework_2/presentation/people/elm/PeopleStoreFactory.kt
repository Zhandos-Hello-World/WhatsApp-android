package kz.tinkoff.homework_2.presentation.people.elm

import vivid.money.elmslie.coroutines.ElmStoreCompat

class PeopleStoreFactory(private val actor: PeopleActor) {
    private val store by lazy {
        ElmStoreCompat(
            initialState = PeopleState.Loading,
            reducer = PeopleReducer(),
            actor = actor
        )
    }

    fun provide() = store
}
