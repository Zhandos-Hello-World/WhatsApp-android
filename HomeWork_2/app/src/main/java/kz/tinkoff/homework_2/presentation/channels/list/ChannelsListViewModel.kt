package kz.tinkoff.homework_2.presentation.channels.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.github.terrakok.cicerone.Router
import kz.tinkoff.homework_2.mapper.ChannelDvoMapper
import kz.tinkoff.homework_2.navigation.Screens
import kz.tinkoff.homework_2.presentation.delegates.channels.ChannelDelegateItem

class ChannelsListViewModel(
    private val factory: ChannelsFactory,
    private val channelDvoMapper: ChannelDvoMapper,
    private val router: Router,
) : ViewModel() {
    private val _channels = MutableLiveData<List<ChannelDelegateItem>>()
    val channels: LiveData<List<ChannelDelegateItem>> = _channels

    init {
        _channels.value = channelDvoMapper.toChannelsDelegates(factory.createChannelsList())
    }

    fun navigateToMessageScreen() {
        router.navigateTo(Screens.MessageScreen())
    }
}