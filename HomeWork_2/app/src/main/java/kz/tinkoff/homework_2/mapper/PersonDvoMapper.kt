package kz.tinkoff.homework_2.mapper

import kz.tinkoff.core.Mapper
import kz.tinkoff.homework_2.domain.model.PersonModel
import kz.tinkoff.homework_2.presentation.delegates.person.PersonDelegateItem
import kz.tinkoff.homework_2.presentation.delegates.person.PersonDvo

class PersonDvoMapper : Mapper<PersonModel, PersonDvo> {

    override fun map(from: PersonModel): PersonDvo {
        return PersonDvo(
            id = from.id,
            fullName = from.fullName,
            email = from.email,
            isOnline = from.isOnline
        )
    }

    fun toPersonList(from: List<PersonModel>): List<PersonDvo> {
        return from.map { map(it) }
    }

    fun toPersonDelegate(from: PersonDvo): PersonDelegateItem {
        return PersonDelegateItem(
            from.id,
            from
        )
    }

    fun toPersonDelegates(from: List<PersonDvo>): List<PersonDelegateItem> {
        return from.map { toPersonDelegate(it) }
    }

    fun toPersonDelegatesFromModel(from: List<PersonModel>): List<PersonDelegateItem> {
        return toPersonDelegates(from.map { map(it) })
    }
}