package kz.tinkoff.homework_2.presentation.profile.elm

import kz.tinkoff.homework_2.presentation.dvo.ProfileDvo

data class ProfileState(
    val profileDvo: ProfileDvo? = null,
    val error: Boolean = false,
    val isLoading: Boolean = false,
)

sealed class ProfileEvent {

    sealed class Ui : ProfileEvent() {

        object LoadProfile : Ui()

    }

    sealed class Internal : ProfileEvent() {

        data class ProfileLoaded(val data: ProfileDvo) : Internal()

        object ErrorLoading : Internal()
    }
}
class ProfileEffect

sealed class ProfileCommand {
    object LoadProfile : ProfileCommand()
}
