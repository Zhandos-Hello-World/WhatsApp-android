package kz.tinkoff.homework_2.data.mappers

import kz.tinkoff.core.Mapper
import kz.tinkoff.homework_2.data.model.ProfileResponse
import kz.tinkoff.homework_2.domain.model.ProfileModel

class ProfileDataToModelMapper: Mapper<ProfileResponse, ProfileModel> {

    override fun map(from: ProfileResponse): ProfileModel {
        return ProfileModel(
            fullName = from.fullName,
            email = from.email,
            userId = from.userId,
            avatarVersion = from.avatarVersion,
            isAdmin = from.isAdmin,
            isOwner = from.isOwner,
            isGuest = from.isGuest,
            timezone = from.timezone,
            isActive = from.isActive,
            avatarUrl = from.avatarUrl.orEmpty(),
            dateJoined = from.dateJoined,
            deliveredEmail = from.deliveredEmail
        )
    }
}
