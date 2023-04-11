package kz.tinkoff.homework_2.presentation.people.elm

import vivid.money.elmslie.core.store.dsl_reducer.DslReducer

class PeopleReducer : DslReducer<PeopleEvent, PeopleState, PeopleEffect, PeopleCommand>() {

    override fun Result.reduce(event: PeopleEvent): Any? =
        when (event) {
            is PeopleEvent.Internal.PeopleLoaded -> {
                state { copy(peopleDvo = event.data, error = false, isLoading = false) }
            }
            is PeopleEvent.Internal.ErrorLoading -> {
                state { copy(peopleDvo = emptyList(), error = true, isLoading = false) }
            }
            is PeopleEvent.Ui.LoadPeople -> {
                state { copy(peopleDvo = emptyList(), error = false, isLoading = true) }
                commands { +PeopleCommand.LoadPeople }
            }
            is PeopleEvent.Ui.SearchPerson -> {
                state {
                    copy(
                        peopleDvo = emptyList(),
                        searchText = event.text,
                        error = false,
                        isLoading = true,
                    )
                }
                commands { +PeopleCommand.SearchPeople(text = event.text) }
            }
        }
}