package kz.tinkoff.homework_2.presentation.profile.elm

import kz.tinkoff.homework_2.presentation.dvo.ProfileDvo

sealed interface ProfileState {
    data class Data(val profileDvo: ProfileDvo? = null) : ProfileState

    object Error : ProfileState

    object Loading : ProfileState
}

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
