package kz.tinkoff.homework_2.data.repository

import kz.tinkoff.homework_2.data.mappers.PersonMapper
import kz.tinkoff.homework_2.domain.datasource.PeopleRemoteDataSource
import kz.tinkoff.homework_2.domain.model.PersonModel
import kz.tinkoff.homework_2.domain.repository.PeopleRepository

class DefaultPeopleRepository(
    private val dataSource: PeopleRemoteDataSource,
    private val mapper: PersonMapper,
) : PeopleRepository {

    override suspend fun getAllPeople(): List<PersonModel> {
        return mapper.toListPeople(dataSource.getAllPeople())
    }

    override suspend fun findPerson(name: String): List<PersonModel> {
        return mapper.toListPeople(dataSource.findPerson(name))
    }
}