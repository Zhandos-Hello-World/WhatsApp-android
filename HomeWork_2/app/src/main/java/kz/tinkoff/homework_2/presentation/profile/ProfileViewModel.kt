package kz.tinkoff.homework_2.presentation.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kz.tinkoff.homework_2.presentation.dvo.ProfileDvo

class ProfileViewModel: ViewModel() {
    private val _profileInfo = MutableLiveData<ProfileDvo>()
    val profileInfo: LiveData<ProfileDvo> = _profileInfo

    init {
        _profileInfo.value = ProfileDvo(
            "Darrell Steward",
            "In a meeting",
            "online"
        )
    }

}