package kz.tinkoff.homework_2.domain.repository

import kz.tinkoff.homework_2.domain.model.PersonModel

interface PeopleRepository {

    suspend fun getAllPeople(): List<PersonModel>

    suspend fun findPerson(name: String): List<PersonModel>
}