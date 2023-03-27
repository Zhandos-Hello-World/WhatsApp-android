package kz.tinkoff.homework_2.data.mappers

import kz.tinkoff.core.Mapper
import kz.tinkoff.homework_2.data.model.ChannelListResponse
import kz.tinkoff.homework_2.domain.model.ChannelModel

class ChannelMapper : Mapper<ChannelListResponse.ChannelResponse?, ChannelModel> {

    override fun map(from: ChannelListResponse.ChannelResponse?): ChannelModel {
        return ChannelModel(
            id = from?.id ?: 0,
            name = from?.name.orEmpty(),
            testingMessageCount = from?.testingMessageCount ?: 0,
            brushMessageCount = from?.brushMessageCount ?: 0
        )
    }

    fun toListChannel(from: ChannelListResponse?): List<ChannelModel> {
        return from?.list?.map { map(it) }.orEmpty()
    }
}