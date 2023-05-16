package kz.tinkoff.homework_2.data.mappers

import kz.tinkoff.core.Mapper
import kz.tinkoff.homework_2.data.model.StreamListResponse
import kz.tinkoff.homework_2.domain.model.StreamModel

class StreamDataToDomainMapper : Mapper<StreamListResponse.StreamResponse, StreamModel> {

    override fun map(from: StreamListResponse.StreamResponse): StreamModel {
        return StreamModel(id = from.id, name = from.name, topics = emptyList())
    }

    fun toListOfStream(from: StreamListResponse?): List<StreamModel> {
        return from?.streams?.map { map(it) }.orEmpty()
    }

}
