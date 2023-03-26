package kz.tinkoff.homework_2.mapper

import kz.tinkoff.homework_2.presentation.delegates.person.PersonDelegateItem
import kz.tinkoff.homework_2.presentation.delegates.person.PersonModel

class PersonDvoMapper {

    fun toPersonDelegate(from: PersonModel): PersonDelegateItem {
        return PersonDelegateItem(
            from.id,
            from
        )
    }

    fun toPersonDelegates(from: List<PersonModel>): List<PersonDelegateItem> {
        return from.map { toPersonDelegate(it) }
    }
}