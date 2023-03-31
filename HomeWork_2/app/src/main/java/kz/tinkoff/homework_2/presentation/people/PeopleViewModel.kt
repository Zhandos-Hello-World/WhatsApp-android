package kz.tinkoff.homework_2.presentation.people

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CancellationException
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
import kz.tinkoff.coreui.ScreenState
import kz.tinkoff.homework_2.domain.repository.PeopleRepository
import kz.tinkoff.homework_2.presentation.delegates.person.PersonDelegateItem
import kz.tinkoff.homework_2.presentation.mapper.PersonDvoMapper

class PeopleViewModel(
    private val repository: PeopleRepository,
    private val personDvoMapper: PersonDvoMapper,
) : ViewModel() {
    private var cachedPeopleList: List<PersonDelegateItem> = listOf()
    private val _peopleList =
        MutableStateFlow<ScreenState<List<PersonDelegateItem>>>(ScreenState.Loading)
    val peopleList: StateFlow<ScreenState<List<PersonDelegateItem>>> get() = _peopleList.asStateFlow()

    val searchQueryState: MutableSharedFlow<String> = MutableSharedFlow()

    init {
        getAll()
        subscribeToSearchQueryChanges()
    }

    fun getAll() {
        viewModelScope.launch {
            _peopleList.emit(ScreenState.Loading)
            try {
                val response = repository.getAllPeople()
                _peopleList.emit(ScreenState.Data(personDvoMapper.toPersonDelegatesFromModel(
                    response)).also {
                    cachedPeopleList = it.data
                })
            } catch (ex: CancellationException) {
                throw ex
            } catch (ex: Exception) {
                _peopleList.emit(ScreenState.Error)
            }
        }
    }

    fun getAllFromCache() {
        viewModelScope.launch {
            _peopleList.emit(ScreenState.Data(cachedPeopleList))
        }
    }

    private suspend fun searchName(name: String): ScreenState<List<PersonDelegateItem>> {
        _peopleList.emit(ScreenState.Loading)
        var state: ScreenState<List<PersonDelegateItem>> = ScreenState.Error

        state = try {
            val response = repository.findPerson(name)
            ScreenState.Data(personDvoMapper.toPersonDelegatesFromModel(response)).also {
                cachedPeopleList = it.data
            }
        } catch (ex: CancellationException) {
            throw ex
        } catch (ex: Exception) {
            ScreenState.Error
        }
        return state
    }

    private fun subscribeToSearchQueryChanges() {
        searchQueryState.filter { it.isNotEmpty() }.distinctUntilChanged().debounce(700L)
            .flatMapLatest { flow { emit(searchName(it)) } }.onEach {
                _peopleList.emit(it)
            }.flowOn(Dispatchers.Default).launchIn(viewModelScope)
    }

}