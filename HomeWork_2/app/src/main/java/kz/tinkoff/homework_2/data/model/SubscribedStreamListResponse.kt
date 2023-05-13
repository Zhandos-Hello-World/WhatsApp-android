package kz.tinkoff.homework_2.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SubscribedStreamListResponse(
    @SerialName("subscriptions")
    val subscriptions: List<SubscribedStreamResponse>
) {

    @Serializable
    data class SubscribedStreamResponse(
        @SerialName("can_remove_subscribers_group_id")
        val canRemoveSubscribersGroupId: Int,
        @SerialName("color")
        val color: String,
        @SerialName("date_created")
        val dateCreated: Int,
        @SerialName("description")
        val description: String,
        @SerialName("email_address")
        val emailAddress: String,
        @SerialName("first_message_id")
        val firstMessageId: Int,
        @SerialName("history_public_to_subscribers")
        val historyPublicToSubscribers: Boolean,
        @SerialName("in_home_view")
        val inHomeView: Boolean,
        @SerialName("invite_only")
        val inviteOnly: Boolean,
        @SerialName("is_announcement_only")
        val isAnnouncementOnly: Boolean,
        @SerialName("is_muted")
        val isMuted: Boolean,
        @SerialName("is_web_public")
        val isWebPublic: Boolean,
        @SerialName("name")
        val name: String,
        @SerialName("pin_to_top")
        val pinToTop: Boolean,
        @SerialName("rendered_description")
        val renderedDescription: String,
        @SerialName("stream_id")
        val streamId: Int,
        @SerialName("stream_post_policy")
        val streamPostPolicy: Int,
    )

}
