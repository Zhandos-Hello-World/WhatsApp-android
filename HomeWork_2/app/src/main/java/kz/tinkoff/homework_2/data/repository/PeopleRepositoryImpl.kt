package kz.tinkoff.homework_2.data.repository

import javax.inject.Inject
import kz.tinkoff.homework_2.data.mappers.PersonDataToDomainMapper
import kz.tinkoff.homework_2.data.mappers.PresenceDataToDomainMapper
import kz.tinkoff.homework_2.data.mappers.ProfileDataToModelMapper
import kz.tinkoff.homework_2.domain.datasource.PeopleRemoteDataSource
import kz.tinkoff.homework_2.domain.model.PersonModel
import kz.tinkoff.homework_2.domain.model.PresenceModel
import kz.tinkoff.homework_2.domain.model.ProfileModel
import kz.tinkoff.homework_2.domain.repository.PeopleRepository

class PeopleRepositoryImpl @Inject constructor(
    private val dataSource: PeopleRemoteDataSource,
    private val peopleMapper: PersonDataToDomainMapper,
    private val profileDataToModelMapper: ProfileDataToModelMapper,
    private val presenceDataToDomainMapper: PresenceDataToDomainMapper,
) : PeopleRepository {

    override suspend fun getAllPeople(): List<PersonModel> {
        val response = dataSource.getAllPeople()
        return peopleMapper.toListPeople(response)
    }

    override suspend fun findPerson(name: String): List<PersonModel> {
        val response = dataSource.findPerson(name)
        return peopleMapper.toListPeople(response)
    }

    override suspend fun getProfile(): ProfileModel {
        val response = dataSource.getProfile()
        return profileDataToModelMapper.map(response)
    }

    override suspend fun getPresence(userIdOrEmail: String): PresenceModel {
        val response = dataSource.getPresence(userIdOrEmail)
        return presenceDataToDomainMapper.map(response)
    }
}
