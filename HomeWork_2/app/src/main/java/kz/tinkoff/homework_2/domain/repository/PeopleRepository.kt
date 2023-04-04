package kz.tinkoff.homework_2.domain.repository

import kz.tinkoff.homework_2.domain.model.PersonModel
import kz.tinkoff.homework_2.domain.model.PresenceModel
import kz.tinkoff.homework_2.domain.model.ProfileModel

interface PeopleRepository {

    suspend fun getAllPeople(): List<PersonModel>

    suspend fun findPerson(name: String): List<PersonModel>

    suspend fun getProfile(): ProfileModel

    suspend fun getPresence(userIdOrEmail: String): PresenceModel
}