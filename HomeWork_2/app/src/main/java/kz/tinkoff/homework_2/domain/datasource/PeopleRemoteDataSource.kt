package kz.tinkoff.homework_2.domain.datasource

import kz.tinkoff.homework_2.data.model.PeopleListResponse
import kz.tinkoff.homework_2.domain.model.PersonModel

interface PeopleRemoteDataSource {

    suspend fun getAllPeople(): PeopleListResponse

    suspend fun findPerson(name: String): PeopleListResponse
}