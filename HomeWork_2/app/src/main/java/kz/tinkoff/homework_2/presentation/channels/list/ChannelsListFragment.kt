package kz.tinkoff.homework_2.presentation.channels.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import kz.tinkoff.core.adapter.AdapterDelegate
import kz.tinkoff.core.adapter.DelegateItem
import kz.tinkoff.core.adapter.MainAdapter
import kz.tinkoff.homework_2.databinding.FragmentChannelListBinding
import kz.tinkoff.homework_2.presentation.channels.SearchEditTextController
import kz.tinkoff.homework_2.presentation.channels.elm.ChannelEffect
import kz.tinkoff.homework_2.presentation.channels.elm.ChannelEvent
import kz.tinkoff.homework_2.presentation.channels.elm.ChannelState
import kz.tinkoff.homework_2.presentation.channels.elm.ChannelStoreFactory
import kz.tinkoff.homework_2.presentation.delegates.channels.ChannelDelegate
import kz.tinkoff.homework_2.presentation.message.MessageArgs
import org.koin.android.ext.android.inject
import vivid.money.elmslie.android.base.ElmFragment

class ChannelsListFragment : ElmFragment<ChannelEvent, ChannelEffect, ChannelState>() {
    private var _binding: FragmentChannelListBinding? = null
    private val binding get() = _binding!!

    private val channelStoreFactory: ChannelStoreFactory by inject()
    override val initEvent: ChannelEvent = ChannelEvent.Ui.LoadChannel

    private val channelRecyclerView: RecyclerView get() = binding.recyclerChannel
    private val errorState: ViewGroup get() = binding.errorState
    private val loadingState: ViewGroup get() = binding.loadingState

    private val delegate: ChannelDelegate by lazy(LazyThreadSafetyMode.NONE) {
        ChannelDelegate(listener = ::navigateToMessage)
    }
    private val adapter: MainAdapter by lazy(LazyThreadSafetyMode.NONE) {
        MainAdapter().apply {
            addDelegate(delegate as AdapterDelegate<RecyclerView.ViewHolder, DelegateItem>)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentChannelListBinding.inflate(inflater, container, false)
        binding.recyclerChannel.adapter = adapter


        (parentFragment as SearchEditTextController).searchEditText { searchText ->
            if (searchText.isNotEmpty()) {
                store.accept(ChannelEvent.Ui.SearchChannel(searchText))
            } else {
                getAll()
            }
        }

        return binding.root
    }

    override fun createStore() = channelStoreFactory.provide()

    override fun render(state: ChannelState) {
        loadingState.isVisible = state.isLoading
        errorState.isVisible = state.error
        channelRecyclerView.isVisible = state.channels.isNotEmpty()

        adapter.submitList(state.channels)
    }

    private fun navigateToMessage(args: MessageArgs) {
        store.accept(ChannelEvent.Ui.NavigateToMessage(args))
    }

    fun getAll() {
        store.accept(ChannelEvent.Ui.LoadChannel)
    }


    companion object {
        fun getINSTANCE(): ChannelsListFragment {
            return ChannelsListFragment()
        }
    }
}