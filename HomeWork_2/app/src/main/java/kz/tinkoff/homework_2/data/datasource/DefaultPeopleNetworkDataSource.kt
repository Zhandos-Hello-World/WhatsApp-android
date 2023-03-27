package kz.tinkoff.homework_2.data.datasource

import kz.tinkoff.homework_2.data.mappers.PersonMapper
import kz.tinkoff.homework_2.data.network.ApiService
import kz.tinkoff.homework_2.domain.datasource.PeopleRemoteDataSource
import kz.tinkoff.homework_2.domain.model.PersonModel

class DefaultPeopleNetworkDataSource(
    private val apiService: ApiService,
    private val mapper: PersonMapper,
) : PeopleRemoteDataSource {

    override suspend fun getAllPeople(): List<PersonModel> {
        return mapper.toListPeople(apiService.getAllPeople())
    }

    override suspend fun findPerson(name: String): List<PersonModel> {
        return mapper.toListPeople(apiService.findPerson(name))
    }
}