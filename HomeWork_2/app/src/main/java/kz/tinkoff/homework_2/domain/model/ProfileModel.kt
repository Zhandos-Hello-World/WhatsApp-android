package kz.tinkoff.homework_2.domain.model


data class ProfileModel(
    val fullName: String,
    val email: String,
    val userId: Int,
    val avatarVersion: Int,
    val isAdmin: Boolean,
    val isOwner: Boolean,
    val isGuest: Boolean,
    val timezone: String,
    val isActive: Boolean,
    val avatarUrl: String,
    val dateJoined: String,
    val deliveredEmail: String
)