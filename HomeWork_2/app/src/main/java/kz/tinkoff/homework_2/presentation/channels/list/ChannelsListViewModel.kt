package kz.tinkoff.homework_2.presentation.channels.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.terrakok.cicerone.Router
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
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
import kz.tinkoff.core.ktx.runCatchingNonCancellation
import kz.tinkoff.coreui.ScreenState
import kz.tinkoff.homework_2.domain.repository.ChannelRepository
import kz.tinkoff.homework_2.navigation.Screens
import kz.tinkoff.homework_2.presentation.delegates.channels.ChannelDelegateItem
import kz.tinkoff.homework_2.presentation.mapper.StreamDvoMapper
import kz.tinkoff.homework_2.presentation.message.MessageArgs

class ChannelsListViewModel(
    private val repository: ChannelRepository,
    private val mapper: StreamDvoMapper,
    private val router: Router,
) : ViewModel() {
    private var cachedChannelsList: List<ChannelDelegateItem> = listOf()
    private val _channels =
        MutableStateFlow<ScreenState<List<ChannelDelegateItem>>>(ScreenState.Loading)
    val channels: StateFlow<ScreenState<List<ChannelDelegateItem>>> get() = _channels.asStateFlow()

    val searchQueryState: MutableSharedFlow<String> = MutableSharedFlow()

    init {
        getAllChannels()
        subscribeToSearchQueryChanges()
    }

    private fun getAllChannels() {
        viewModelScope.launch {
            try {
                _channels.emit(ScreenState.Loading)

                val responseStreams = runCatchingNonCancellation {
                    repository.getAllChannels()
                }.getOrNull()

                if (responseStreams == null) {
                    _channels.emit(ScreenState.Error)
                    return@launch
                }

                val topicDeferred =
                    responseStreams.map { channelModel -> getTopicsByIdAsync(channelModel.id) }

                val responseTopics = topicDeferred.awaitAll().filterNotNull()

                responseStreams.forEachIndexed { index, streamModel ->
                    streamModel.topics = responseTopics[index].topics
                }

                _channels.emit(
                    ScreenState.Data(
                        mapper.toChannelsDelegateItems(responseStreams).also {
                            cachedChannelsList = it
                        }
                    )
                )
            } catch (ex: CancellationException) {
                throw ex
            } catch (ex: Exception) {
                _channels.emit(
                    ScreenState.Error
                )
            }
        }
    }

    private fun getTopicsByIdAsync(id: Int) = viewModelScope.async {
        runCatchingNonCancellation {
            repository.findTopics(id)
        }.getOrNull()
    }

    fun getAllChannelsFromCashed() {
        viewModelScope.launch {
            _channels.emit(
                ScreenState.Data(
                    cachedChannelsList
                )
            )
        }
    }

    private suspend fun searchName(name: String): ScreenState<List<ChannelDelegateItem>> {
        _channels.emit(ScreenState.Loading)

        val response = runCatchingNonCancellation { repository.findChannels(name) }.getOrNull()

        return if (response != null) {
            ScreenState.Data(mapper.toChannelsDelegateItems(response))
        } else {
            ScreenState.Error
        }
    }

    private fun subscribeToSearchQueryChanges() {
        searchQueryState
            .filter { it.isNotEmpty() }
            .distinctUntilChanged()
            .debounce(700L)
            .flatMapLatest { flow { emit(searchName(it)) } }
            .onEach {
                _channels.emit(it)
            }
            .flowOn(Dispatchers.Default)
            .launchIn(viewModelScope)
    }

    fun navigateToMessageScreen(args: MessageArgs) {
        router.navigateTo(Screens.MessageScreen(args))
    }
}