package kz.tinkoff.homework_2.data.mappers

import kz.tinkoff.core.Mapper
import kz.tinkoff.homework_2.data.model.PresenceResponse
import kz.tinkoff.homework_2.domain.model.PresenceModel

class PresenceDataToDomainMapper: Mapper<PresenceResponse, PresenceModel> {

    override fun map(from: PresenceResponse): PresenceModel {
        return PresenceModel(
            status = from.presence.aggregated.status
        )
    }
}
