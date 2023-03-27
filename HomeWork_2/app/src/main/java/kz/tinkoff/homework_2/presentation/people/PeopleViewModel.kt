package kz.tinkoff.homework_2.presentation.people

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import kz.tinkoff.coreui.BaseViewModel
import kz.tinkoff.coreui.ScreenState
import kz.tinkoff.homework_2.domain.repository.PeopleRepository
import kz.tinkoff.homework_2.mapper.PersonDvoMapper
import kz.tinkoff.homework_2.presentation.delegates.person.PersonDelegateItem

class PeopleViewModel(
    private val repository: PeopleRepository,
    private val personDvoMapper: PersonDvoMapper,
) : BaseViewModel() {
    private var cachedPeopleList: List<PersonDelegateItem> = mutableListOf()
    private val _peopleList =
        MutableStateFlow<ScreenState<List<PersonDelegateItem>>>(ScreenState.Loading)
    val peopleList: StateFlow<ScreenState<List<PersonDelegateItem>>> get() = _peopleList.asStateFlow()

    val searchQueryState: MutableSharedFlow<String> = MutableSharedFlow()

    init {
        getAll()
        subscribeToSearchQueryChanges()
    }

    fun getAll() {
        networkRequest(
            request = { repository.getAllPeople() },
            onSuccess = { response ->
                _peopleList.emit(
                    ScreenState.Data(personDvoMapper.toPersonDelegatesFromModel(response)).also {
                        cachedPeopleList = it.data
                    }
                )
            },
            onFail = { _peopleList.emit(ScreenState.Error(it)) },
            onBeforeRequest = { _peopleList.emit(ScreenState.Loading) }
        )
    }

    fun getAllFromCache() {
        viewModelScope.launch {
            _peopleList.emit(
                ScreenState.Data(
                    cachedPeopleList
                )
            )
        }
    }

    suspend fun searchName(name: String): List<PersonDelegateItem> {
        _peopleList.emit(ScreenState.Loading)
        return personDvoMapper.toPersonDelegatesFromModel(repository.findPerson(name))
    }

    private fun subscribeToSearchQueryChanges() {
        searchQueryState
            .filter { it.isNotEmpty() }
            .distinctUntilChanged()
            .debounce(700L)
            .flatMapLatest { flow { emit(searchName(it)) } }
            .onEach {
                _peopleList.emit(ScreenState.Data(it))
            }
            .flowOn(Dispatchers.Default)
            .launchIn(viewModelScope)
    }

}