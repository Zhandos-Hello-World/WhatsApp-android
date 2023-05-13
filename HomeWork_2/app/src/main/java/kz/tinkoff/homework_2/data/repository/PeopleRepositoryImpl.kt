package kz.tinkoff.homework_2.data.repository

import javax.inject.Inject
import kz.tinkoff.homework_2.data.mappers.PersonMapper
import kz.tinkoff.homework_2.data.mappers.PresenceMapper
import kz.tinkoff.homework_2.data.mappers.ProfileMapper
import kz.tinkoff.homework_2.domain.datasource.PeopleRemoteDataSource
import kz.tinkoff.homework_2.domain.model.PersonModel
import kz.tinkoff.homework_2.domain.model.PresenceModel
import kz.tinkoff.homework_2.domain.model.ProfileModel
import kz.tinkoff.homework_2.domain.repository.PeopleRepository

class PeopleRepositoryImpl @Inject constructor(
    private val dataSource: PeopleRemoteDataSource,
    private val peopleMapper: PersonMapper,
    private val profileMapper: ProfileMapper,
    private val presenceMapper: PresenceMapper,
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
        return profileMapper.map(response)
    }

    override suspend fun getPresence(userIdOrEmail: String): PresenceModel {
        val response = dataSource.getPresence(userIdOrEmail)
        return presenceMapper.map(response)
    }
}
