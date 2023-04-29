package kz.tinkoff.homework_2.presentation.people.elm

import vivid.money.elmslie.core.store.dsl_reducer.DslReducer

class PeopleReducer : DslReducer<PeopleEvent, PeopleState, PeopleEffect, PeopleCommand>() {

    override fun Result.reduce(event: PeopleEvent): Any? =
        when (event) {
            is PeopleEvent.Internal.PeopleLoaded -> {
                state { PeopleState.Data(event.data) }
            }
            is PeopleEvent.Internal.ErrorLoading -> {
                state { PeopleState.Error }
            }
            is PeopleEvent.Ui.LoadPeople -> {
                state { PeopleState.Loading }
                commands { +PeopleCommand.LoadPeople }
            }
            is PeopleEvent.Ui.SearchPerson -> {
                commands { +PeopleCommand.SearchPeople(text = event.text) }
            }
        }
}
