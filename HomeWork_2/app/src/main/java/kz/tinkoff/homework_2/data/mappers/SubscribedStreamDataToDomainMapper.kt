package kz.tinkoff.homework_2.data.mappers

import kz.tinkoff.core.Mapper
import kz.tinkoff.homework_2.data.model.SubscribedStreamListResponse
import kz.tinkoff.homework_2.domain.model.SubscribedStreamModel

class SubscribedStreamDataToDomainMapper :
    Mapper<SubscribedStreamListResponse.SubscribedStreamResponse, SubscribedStreamModel> {

    override fun map(from: SubscribedStreamListResponse.SubscribedStreamResponse): SubscribedStreamModel {
        return SubscribedStreamModel(
            canRemoveSubscribersGroupId = from.canRemoveSubscribersGroupId,
            color = from.color,
            dateCreated = from.dateCreated,
            description = from.description,
            emailAddress = from.emailAddress,
            firstMessageId = from.firstMessageId,
            historyPublicToSubscribers = from.historyPublicToSubscribers,
            inHomeView = from.inHomeView,
            inviteOnly = from.inviteOnly,
            isAnnouncementOnly = from.isAnnouncementOnly,
            isMuted = from.isMuted,
            isWebPublic = from.isWebPublic,
            name = from.name,
            pinToTop = from.pinToTop,
            renderedDescription = from.renderedDescription,
            streamId = from.streamId,
            streamPostPolicy = from.streamPostPolicy,
        )
    }
}
