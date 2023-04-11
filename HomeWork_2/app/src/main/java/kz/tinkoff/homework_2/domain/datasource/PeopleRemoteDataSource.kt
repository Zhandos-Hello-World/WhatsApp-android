package kz.tinkoff.homework_2.domain.datasource

import kz.tinkoff.homework_2.data.model.PeopleListResponse
import kz.tinkoff.homework_2.data.model.PresenceResponse
import kz.tinkoff.homework_2.data.model.ProfileResponse

interface PeopleRemoteDataSource {

    suspend fun getAllPeople(): PeopleListResponse

    suspend fun findPerson(name: String): PeopleListResponse

    suspend fun getProfile(): ProfileResponse

    suspend fun getPresence(userIdOrEmail: String): PresenceResponse

}