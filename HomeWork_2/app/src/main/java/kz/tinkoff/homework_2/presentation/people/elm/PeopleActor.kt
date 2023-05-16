package kz.tinkoff.homework_2.presentation.people.elm

import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kz.tinkoff.core.ktx.runCatchingNonCancellation
import kz.tinkoff.homework_2.domain.repository.PeopleRepository
import kz.tinkoff.homework_2.presentation.delegates.person.PersonDelegateItem
import kz.tinkoff.homework_2.presentation.mapper.PersonDvoMapper
import vivid.money.elmslie.coroutines.Actor

class PeopleActor @Inject constructor(
    private val repository: PeopleRepository,
    private val dvoMapper: PersonDvoMapper,
) : Actor<PeopleCommand, PeopleEvent> {

    override fun execute(command: PeopleCommand): Flow<PeopleEvent> = when (command) {
        is PeopleCommand.LoadPeople -> {
            flow<PeopleEvent> {
                val response = repository.getAllPeople()
                dvoMapper.toPersonDelegatesFromModel(response)
                emit(
                    PeopleEvent.Internal.PeopleLoaded(
                        dvoMapper.toPersonDelegatesFromModel(response)
                    )
                )
            }.catch {
                emit(PeopleEvent.Internal.ErrorLoading)
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
