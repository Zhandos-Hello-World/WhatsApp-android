package kz.tinkoff.homework_2.data.repository

import kz.tinkoff.homework_2.domain.datasource.PeopleRemoteDataSource
import kz.tinkoff.homework_2.domain.model.PersonModel
import kz.tinkoff.homework_2.domain.repository.PeopleRepository

class DefaultPeopleRepository(private val dataSource: PeopleRemoteDataSource) : PeopleRepository {

    override suspend fun getAllPeople(): List<PersonModel> {
        return dataSource.getAllPeople()
    }

    override suspend fun findPerson(name: String): List<PersonModel> {
        return dataSource.findPerson(name)
    }
}