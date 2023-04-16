package kz.tinkoff.homework_2.presentation.people.elm

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
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
import kz.tinkoff.core.ktx.runCatchingNonCancellation
import kz.tinkoff.homework_2.domain.repository.PeopleRepository
import kz.tinkoff.homework_2.presentation.delegates.person.PersonDelegateItem
import kz.tinkoff.homework_2.presentation.mapper.PersonDvoMapper
import vivid.money.elmslie.coroutines.Actor

class PeopleActor(
    private val repository: PeopleRepository,
    private val dvoMapper: PersonDvoMapper,
) : Actor<PeopleCommand, PeopleEvent> {

    override fun execute(command: PeopleCommand): Flow<PeopleEvent> = when (command) {
        is PeopleCommand.LoadPeople -> {
            flow<PeopleEvent> {
                val response = runCatchingNonCancellation {
                    repository.getAllPeople()
                }.getOrNull()

                if (response != null) {
                    dvoMapper.toPersonDelegatesFromModel(response)
                    emit(
                        PeopleEvent.Internal.PeopleLoaded(
                            dvoMapper.toPersonDelegatesFromModel(response)
                        )
                    )
                } else {
                    emit(PeopleEvent.Internal.ErrorLoading)
                }
            }
        }
        is PeopleCommand.SearchPeople -> {
            flow {
                val response = searchName(command.text)
                emit(PeopleEvent.Internal.PeopleLoaded(response))
            }
        }
    }

    private suspend fun searchName(name: String): List<PersonDelegateItem> {
        val result = runCatchingNonCancellation {
            repository.findPerson(name)
        }.getOrNull()

        return if (result != null) {
            dvoMapper.toPersonDelegatesFromModel(result)
        } else {
            emptyList()
        }
    }

}
