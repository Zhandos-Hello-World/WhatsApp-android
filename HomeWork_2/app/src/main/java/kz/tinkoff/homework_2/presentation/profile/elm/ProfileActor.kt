package kz.tinkoff.homework_2.presentation.profile.elm

import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kz.tinkoff.homework_2.domain.repository.PeopleRepository
import kz.tinkoff.homework_2.presentation.mapper.ProfileDvoMapper
import vivid.money.elmslie.coroutines.Actor

class ProfileActor @Inject constructor(
    private val repository: PeopleRepository,
    private val dvoMapper: ProfileDvoMapper,
) : Actor<ProfileCommand, ProfileEvent> {


    override fun execute(command: ProfileCommand): Flow<ProfileEvent> = when (command) {
        is ProfileCommand.LoadProfile -> {
            flow<ProfileEvent> {
                val responseProfile = repository.getProfile()
                val responseConnectionStatus =
                    repository.getPresence(responseProfile.userId.toString())

                emit(
                    ProfileEvent.Internal.ProfileLoaded(
                        dvoMapper.toProfileDvoWithConnectionStatus(
                            responseProfile,
                            responseConnectionStatus
                        )
                    )
                )

            }.catch {
                emit(ProfileEvent.Internal.ErrorLoading)
            }
        }
    }
}
