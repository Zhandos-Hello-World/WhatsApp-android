package kz.tinkoff.homework_2.presentation.profile.elm

import vivid.money.elmslie.core.store.dsl_reducer.DslReducer

class ProfileReducer : DslReducer<ProfileEvent, ProfileState, ProfileEffect, ProfileCommand>() {

    override fun Result.reduce(event: ProfileEvent): Any {
        return when (event) {
            is ProfileEvent.Internal.ErrorLoading -> {
                state { ProfileState.Error }
            }
            is ProfileEvent.Internal.ProfileLoaded -> {
                state { ProfileState.Data(event.data) }
            }
            is ProfileEvent.Ui.LoadProfile -> {
                state { ProfileState.Loading }
                commands { +ProfileCommand.LoadProfile }
            }
        }
    }
}
