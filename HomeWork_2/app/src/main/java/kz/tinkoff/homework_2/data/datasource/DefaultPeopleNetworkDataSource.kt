package kz.tinkoff.homework_2.data.datasource

import kz.tinkoff.homework_2.data.model.PeopleListResponse
import kz.tinkoff.homework_2.data.network.ApiService
import kz.tinkoff.homework_2.domain.datasource.PeopleRemoteDataSource

class DefaultPeopleNetworkDataSource(
    private val apiService: ApiService,
) : PeopleRemoteDataSource {

    override suspend fun getAllPeople(): PeopleListResponse {
        return apiService.getAllPeople()
    }

    override suspend fun findPerson(name: String): PeopleListResponse {
        return apiService.findPerson(name)
    }
}