package kz.tinkoff.homework_2.presentation.people.elm

import kz.tinkoff.homework_2.presentation.delegates.person.PersonDelegateItem

data class PeopleState(
    val peopleDvo: List<PersonDelegateItem> = emptyList(),
    val searchText: String = "",
    val error: Boolean = false,
    val isLoading: Boolean = false,
)

sealed class PeopleEvent {

    sealed class Ui : PeopleEvent() {

        object LoadPeople : Ui()

        data class SearchPerson(val text: String): Ui()

    }

    sealed class Internal : PeopleEvent() {

        data class PeopleLoaded(val data: List<PersonDelegateItem>) : Internal()

        object ErrorLoading : Internal()
    }
}

class PeopleEffect

sealed class PeopleCommand {
    object LoadPeople : PeopleCommand()
    data class SearchPeople(val text: String) : PeopleCommand()
}
