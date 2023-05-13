package kz.tinkoff.homework_2.presentation.create_stream.elm

import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kz.tinkoff.homework_2.domain.model.CreateStreamParams
import kz.tinkoff.homework_2.domain.repository.StreamRepository
import vivid.money.elmslie.coroutines.Actor

class CreateStreamActor @Inject constructor(
    private val repo: StreamRepository,
) : Actor<CreateStreamCommand, CreateStreamEvent> {

    override fun execute(command: CreateStreamCommand): Flow<CreateStreamEvent> {
        return when (command) {
            is CreateStreamCommand.CreateStreamRequest -> {
                flow<CreateStreamEvent> {
                    if (command.name.isEmpty()) {
                        emit(CreateStreamEvent.Internal.ErrorLoading)
                        return@flow
                    }

                    val params = CreateStreamParams(
                        name = command.name,
                        description = command.desc
                    )
                    val success = repo.createStream(params)
                    if (success) {
                        emit(CreateStreamEvent.Internal.CreateStreamSuccess)
                    } else {
                        emit(CreateStreamEvent.Internal.ErrorLoading)
                    }
                }.catch {
                    emit(CreateStreamEvent.Internal.ErrorLoading)
                }
            }
        }
    }
}
