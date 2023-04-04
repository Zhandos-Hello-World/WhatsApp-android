package kz.tinkoff.homework_2.presentation.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kz.tinkoff.core.ktx.runCatchingNonCancellation
import kz.tinkoff.coreui.ScreenState
import kz.tinkoff.homework_2.domain.repository.PeopleRepository
import kz.tinkoff.homework_2.presentation.dvo.ProfileDvo
import kz.tinkoff.homework_2.presentation.mapper.ProfileDvoMapper

class ProfileViewModel(
    private val repository: PeopleRepository,
    private val mapper: ProfileDvoMapper,
) : ViewModel() {
    private val _profileInfo = MutableStateFlow<ScreenState<ProfileDvo>>(ScreenState.Loading)
    val profileInfo: StateFlow<ScreenState<ProfileDvo>> get() = _profileInfo.asStateFlow()

    init {
        getProfile()
    }

    fun getProfile() {
        viewModelScope.launch {
            try {
                _profileInfo.emit(ScreenState.Loading)

                val responseProfile = runCatchingNonCancellation {
                    repository.getProfile()
                }.getOrNull()

                if (responseProfile == null) {
                    _profileInfo.emit(ScreenState.Error)
                    return@launch
                }

                val presenceResponse = runCatchingNonCancellation {
                    repository.getPresence(responseProfile.userId.toString())
                }.getOrNull()

                if (presenceResponse == null) {
                    _profileInfo.emit(ScreenState.Error)
                    return@launch
                }

                val profileDvo =
                    mapper.toProfileDvoWithConnectionStatus(responseProfile, presenceResponse)

                _profileInfo.emit(ScreenState.Data(profileDvo))
            } catch (ex: CancellationException) {
                throw ex
            } catch (ex: Exception) {
                ex
                _profileInfo.emit(ScreenState.Error)
            }
        }
    }

}