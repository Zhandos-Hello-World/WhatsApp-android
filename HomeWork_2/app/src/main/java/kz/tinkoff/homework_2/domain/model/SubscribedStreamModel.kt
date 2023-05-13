package kz.tinkoff.homework_2.domain.model

data class SubscribedStreamModel(
    val canRemoveSubscribersGroupId: Int,
    val color: String,
    val dateCreated: Int,
    val description: String,
    val emailAddress: String,
    val firstMessageId: Int,
    val historyPublicToSubscribers: Boolean,
    val inHomeView: Boolean,
    val inviteOnly: Boolean,
    val isAnnouncementOnly: Boolean,
    val isMuted: Boolean,
    val isWebPublic: Boolean,
    val name: String,
    val pinToTop: Boolean,
    val renderedDescription: String,
    val streamId: Int,
    val streamPostPolicy: Int,
)
