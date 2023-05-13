package kz.tinkoff.homework_2.presentation.mapper

import kz.tinkoff.core.Mapper
import kz.tinkoff.homework_2.domain.model.SubscribedStreamModel
import kz.tinkoff.homework_2.presentation.dvo.StreamDvo

class SubscribedStreamDvoMapper: Mapper<SubscribedStreamModel, StreamDvo> {

    override fun map(from: SubscribedStreamModel): StreamDvo {
        return StreamDvo(
            id = from.streamId,
            name = from.name,
            topicsDvo = emptyList(),
        )
    }
}
