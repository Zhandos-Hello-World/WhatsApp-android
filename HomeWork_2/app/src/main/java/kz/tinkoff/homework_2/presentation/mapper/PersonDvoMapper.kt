package kz.tinkoff.homework_2.presentation.mapper

import kz.tinkoff.core.Mapper
import kz.tinkoff.homework_2.domain.model.PersonModel
import kz.tinkoff.homework_2.presentation.delegates.person.PersonDelegateItem
import kz.tinkoff.homework_2.presentation.dvo.PersonDvo

class PersonDvoMapper(private val delegateItemMapper: PersonDelegateItemMapper) :
    Mapper<PersonModel, PersonDvo> {

    override fun map(from: PersonModel): PersonDvo {
        return PersonDvo(
            id = from.id,
            fullName = from.fullName,
            email = from.email,
            isOnline = from.isOnline
        )
    }


    fun toPersonDelegatesFromModel(from: List<PersonModel>): List<PersonDelegateItem> {
        return delegateItemMapper.map(from.map { map(it) })
    }
}
