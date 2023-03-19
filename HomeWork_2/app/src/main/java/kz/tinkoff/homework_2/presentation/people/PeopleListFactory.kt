package kz.tinkoff.homework_2.presentation.people

import kz.tinkoff.homework_2.presentation.delegates.person.PersonModel

interface PeopleListFactory {
    fun createReactionList(): List<PersonModel>
}

class DefaultPeopleListFactory : PeopleListFactory {

    override fun createReactionList(): List<PersonModel> {
        val list = buildList<PersonModel> {
            for (i in 0..9) {
                add(PersonModel(i, "Darrell Steward", "darrel@company.com", i % 2 == 0))
            }
        }
        return list
    }
}