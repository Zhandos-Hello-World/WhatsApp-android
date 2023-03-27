package kz.tinkoff.homework_2.domain.datasource

import kz.tinkoff.homework_2.domain.model.PersonModel

interface PeopleRemoteDataSource {

    suspend fun getAllPeople(): List<PersonModel>

    suspend fun findPerson(name: String): List<PersonModel>
}