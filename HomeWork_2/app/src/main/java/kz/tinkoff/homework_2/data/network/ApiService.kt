package kz.tinkoff.homework_2.data.network

import kz.tinkoff.homework_2.data.model.ChannelListResponse
import kz.tinkoff.homework_2.data.model.PeopleListResponse

interface ApiService {

    suspend fun getAllChannels(): ChannelListResponse?

    suspend fun findChannels(name: String): ChannelListResponse?

    suspend fun getAllPeople(): PeopleListResponse?

    suspend fun findPerson(name: String): PeopleListResponse?

}