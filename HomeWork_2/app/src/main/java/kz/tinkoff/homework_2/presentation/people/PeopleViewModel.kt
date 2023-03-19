package kz.tinkoff.homework_2.presentation.people

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kz.tinkoff.homework_2.mapper.PersonDvoMapper
import kz.tinkoff.homework_2.presentation.delegates.person.PersonDelegateItem

class PeopleViewModel(
    private val factory: PeopleListFactory,
    private val personDvoMapper: PersonDvoMapper,
) : ViewModel() {
    private val _peopleList = MutableLiveData<List<PersonDelegateItem>>()
    val peopleList: LiveData<List<PersonDelegateItem>> = _peopleList


    init {
        _peopleList.value = personDvoMapper.toPersonDelegates(factory.createReactionList())
    }

}