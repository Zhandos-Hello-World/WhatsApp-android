package kz.tinkoff.homework_2.presentation.profile.elm

import javax.inject.Inject
import vivid.money.elmslie.coroutines.ElmStoreCompat

class ProfileStoreFactory @Inject constructor(private val actor: ProfileActor) {
    private val store by lazy {
        ElmStoreCompat(
            initialState = ProfileState.Loading,
            reducer = ProfileReducer(),
            actor = actor
        )
    }

    fun provide() = store
}
