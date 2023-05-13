package kz.tinkoff.homework_2.data.mappers

import com.google.gson.Gson
import kz.tinkoff.core.Mapper
import kz.tinkoff.homework_2.domain.model.CreateStreamParams

class CreateStreamDtoMapper : Mapper<CreateStreamParams, HashMap<String, String>> {

    override fun map(from: CreateStreamParams): HashMap<String, String> {
        val filters = hashMapOf<String, String>()
        filters[SUBSCRIPTIONS] = Gson().toJson(listOf(from))
        return filters
    }


    companion object {
        private const val SUBSCRIPTIONS = "subscriptions"
    }
}
