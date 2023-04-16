package kz.tinkoff.homework_2.presentation.mapper

import kz.tinkoff.core.Mapper
import kz.tinkoff.homework_2.presentation.delegates.person.PersonDelegateItem
import kz.tinkoff.homework_2.presentation.dvo.PersonDvo

class PersonDelegateItemMapper : Mapper<List<PersonDvo>, List<PersonDelegateItem>> {

    override fun map(from: List<PersonDvo>): List<PersonDelegateItem> {
        return from.map { toPersonDelegate(it) }
    }

    private fun toPersonDelegate(from: PersonDvo): PersonDelegateItem {
        return PersonDelegateItem(
            from.id,
            from
        )
    }

}
