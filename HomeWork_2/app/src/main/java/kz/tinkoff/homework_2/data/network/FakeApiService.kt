package kz.tinkoff.homework_2.data.network

import android.accounts.NetworkErrorException
import kotlinx.coroutines.delay
import kz.tinkoff.homework_2.data.model.ChannelListResponse
import kz.tinkoff.homework_2.data.model.PeopleListResponse

class FakeApiService(private val factory: FakeCommonFactory) : ApiService {

    override suspend fun getAllChannels(): ChannelListResponse {
        delay(1000L)
        return factory.getChannels()
    }

    override suspend fun findChannels(name: String): ChannelListResponse {
        delay(1000L)
        return factory.findChannel(name)
    }

    override suspend fun getAllPeople(): PeopleListResponse {
        delay(1000L)
        if ((0..1).random() == 1) {
            throw NetworkErrorException()
        }
        return factory.getPeople()
    }

    override suspend fun findPerson(name: String): PeopleListResponse {
        delay(1000L)
        return factory.findPerson(name)
    }
}