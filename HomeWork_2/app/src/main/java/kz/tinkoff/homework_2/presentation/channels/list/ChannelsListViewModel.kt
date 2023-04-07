package kz.tinkoff.homework_2.presentation.channels.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.terrakok.cicerone.Router
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
import kz.tinkoff.core.ktx.runCatchingNonCancellation
import kz.tinkoff.coreui.ScreenState
import kz.tinkoff.homework_2.domain.repository.ChannelRepository
import kz.tinkoff.homework_2.presentation.mapper.ChannelDvoMapper
import kz.tinkoff.homework_2.navigation.Screens
import kz.tinkoff.homework_2.presentation.delegates.channels.ChannelDelegateItem

class ChannelsListViewModel(
    private val repository: ChannelRepository,
    private val mapper: ChannelDvoMapper,
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
            val result = runCatchingNonCancellation {
                repository.getAllChannels()
            }.getOrNull()

            val state = if (result != null) {
                ScreenState.Data(
                    mapper.toChannelsDelegateItems(result).also {
                        cachedChannelsList = it
                    }
                )
            } else {
                ScreenState.Error
            }
            _channels.emit(state)
        }
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

        val result = runCatchingNonCancellation {
            repository.findChannels(name)
        }.getOrNull()

        return if (result != null) {
            ScreenState.Data(mapper.toChannelsDelegateItems(result))
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

    fun navigateToMessageScreen() {
        router.navigateTo(Screens.MessageScreen())
    }
}