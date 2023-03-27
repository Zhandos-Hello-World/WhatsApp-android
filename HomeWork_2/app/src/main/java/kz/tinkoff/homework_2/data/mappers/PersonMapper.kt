package kz.tinkoff.homework_2.data.mappers

import kz.tinkoff.core.Mapper
import kz.tinkoff.homework_2.data.model.PeopleListResponse
import kz.tinkoff.homework_2.domain.model.PersonModel

class PersonMapper : Mapper<PeopleListResponse.PersonResponse?, PersonModel> {

    override fun map(from: PeopleListResponse.PersonResponse?): PersonModel {
        return PersonModel(
            id = from?.id ?: 0,
            fullName = from?.fullName.orEmpty(),
            email = from?.email.orEmpty(),
            isOnline = from?.isOnline ?: false
        )
    }


    fun toListPeople(from: PeopleListResponse?): List<PersonModel> {
        return from?.listResponse?.map { map(it) }.orEmpty()
    }

}