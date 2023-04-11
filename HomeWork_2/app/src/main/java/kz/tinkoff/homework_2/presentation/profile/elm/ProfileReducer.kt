package kz.tinkoff.homework_2.presentation.profile.elm

import vivid.money.elmslie.core.store.dsl_reducer.DslReducer

class ProfileReducer : DslReducer<ProfileEvent, ProfileState, ProfileEffect, ProfileCommand>() {

    override fun Result.reduce(event: ProfileEvent): Any {
        return when (event) {
            is ProfileEvent.Internal.ErrorLoading -> {
                state { copy(profileDvo = null, error = true, isLoading = false,) }
            }
            is ProfileEvent.Internal.ProfileLoaded -> {
                state { copy(profileDvo = event.data, error = false, isLoading = false) }
            }
            is ProfileEvent.Ui.LoadProfile -> {
                state { copy(profileDvo = null, error = false, isLoading = true) }
                commands { +ProfileCommand.LoadProfile }
            }
        }
    }
}
