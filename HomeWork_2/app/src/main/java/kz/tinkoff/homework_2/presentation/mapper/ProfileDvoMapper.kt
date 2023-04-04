package kz.tinkoff.homework_2.presentation.mapper

import kz.tinkoff.core.Mapper
import kz.tinkoff.homework_2.domain.model.PresenceModel
import kz.tinkoff.homework_2.domain.model.ProfileModel
import kz.tinkoff.homework_2.presentation.dvo.ProfileDvo

class ProfileDvoMapper : Mapper<ProfileModel, ProfileDvo> {


    override fun map(from: ProfileModel): ProfileDvo {
        return ProfileDvo(fullName = from.fullName, avatarUrl = from.avatarUrl)
    }

    fun toProfileDvoWithConnectionStatus(
        fromProfile: ProfileModel,
        fromPresence: PresenceModel,
    ): ProfileDvo {
        return ProfileDvo(fullName = fromProfile.fullName,
            avatarUrl = fromProfile.avatarUrl,
            presence = ProfileDvo.PRESENCE.values().find { it.status == fromPresence.status }
                ?: ProfileDvo.PRESENCE.OFFLINE)
    }

}