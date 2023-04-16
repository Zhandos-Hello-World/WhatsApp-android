package kz.tinkoff.homework_2.data.datasource

import kz.tinkoff.homework_2.data.model.PeopleListResponse
import kz.tinkoff.homework_2.data.model.PresenceResponse
import kz.tinkoff.homework_2.data.model.ProfileResponse
import kz.tinkoff.homework_2.data.network.PeopleApiService
import kz.tinkoff.homework_2.domain.datasource.PeopleRemoteDataSource

class DefaultPeopleNetworkDataSource(
    private val apiService: PeopleApiService,
) : PeopleRemoteDataSource {

    override suspend fun getAllPeople(): PeopleListResponse {
        return apiService.getAllPeople()
    }

    override suspend fun findPerson(name: String): PeopleListResponse {
        val response = apiService.getAllPeople()
        return response.copy(listResponse = response.listResponse.filter {
            it.fullName.lowercase().contains(name.lowercase())
        })
    }

    override suspend fun getProfile(): ProfileResponse {
        return apiService.getProfile()
    }

    override suspend fun getPresence(userIdOrEmail: String): PresenceResponse {
        return apiService.getPresence(userIdOrEmail)
    }
}
