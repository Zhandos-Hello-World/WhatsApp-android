package kz.tinkoff.homework_2.presentation.profile.elm

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kz.tinkoff.core.ktx.runCatchingNonCancellation
import kz.tinkoff.homework_2.domain.repository.PeopleRepository
import kz.tinkoff.homework_2.presentation.mapper.ProfileDvoMapper
import vivid.money.elmslie.coroutines.Actor

class ProfileActor(
    private val repository: PeopleRepository,
    private val dvoMapper: ProfileDvoMapper,
) : Actor<ProfileCommand, ProfileEvent> {


    override fun execute(command: ProfileCommand): Flow<ProfileEvent> = when (command) {
        is ProfileCommand.LoadProfile -> {
            flow<ProfileEvent> {
                val response = runCatchingNonCancellation {
                    repository.getProfile()
                }.getOrNull()

                if (response != null) {
                    emit(
                        ProfileEvent.Internal.ProfileLoaded(
                            dvoMapper.map(response)
                        )
                    )
                } else {
                    emit(ProfileEvent.Internal.ErrorLoading)
                }
            }
        }
    }
}