package kz.tinkoff.homework_2.presentation.channels.list

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import javax.inject.Inject
import kz.tinkoff.core.adapter.AdapterDelegate
import kz.tinkoff.core.adapter.DelegateItem
import kz.tinkoff.core.adapter.MainAdapter
import kz.tinkoff.core.utils.lazyUnsafe
import kz.tinkoff.homework_2.databinding.FragmentChannelListBinding
import kz.tinkoff.homework_2.di_dagger.stream.DaggerStreamComponent
import kz.tinkoff.homework_2.di_dagger.stream.StreamModule
import kz.tinkoff.homework_2.getAppComponent
import kz.tinkoff.homework_2.presentation.channels.SearchEditTextController
import kz.tinkoff.homework_2.presentation.channels.elm.ChannelEffect
import kz.tinkoff.homework_2.presentation.channels.elm.ChannelEvent
import kz.tinkoff.homework_2.presentation.channels.elm.StreamState
import kz.tinkoff.homework_2.presentation.channels.elm.StreamStoreFactory
import kz.tinkoff.homework_2.presentation.delegates.channels.ChannelDelegate
import kz.tinkoff.homework_2.presentation.message.MessageArgs
import vivid.money.elmslie.android.base.ElmFragment
import vivid.money.elmslie.android.storeholder.LifecycleAwareStoreHolder

class ChannelsListFragment : ElmFragment<ChannelEvent, ChannelEffect, StreamState>() {
    private var _binding: FragmentChannelListBinding? = null
    private val binding get() = _binding!!

    override val initEvent: ChannelEvent = ChannelEvent.Ui.LoadChannel

    private val channelRecyclerView: RecyclerView get() = binding.recyclerChannel
    private val errorState: ViewGroup get() = binding.errorState
    private val loadingState: ViewGroup get() = binding.loadingState

    @Inject
    lateinit var storeFactory: StreamStoreFactory

    override val storeHolder by lazyUnsafe {
        LifecycleAwareStoreHolder(lifecycle) {
            storeFactory.provide()
        }
    }

    private val delegate: ChannelDelegate by lazyUnsafe {
        ChannelDelegate(listener = ::navigateToMessage)
    }

    private val adapter: MainAdapter by lazyUnsafe {
        MainAdapter().apply {
            addDelegate(delegate as AdapterDelegate<RecyclerView.ViewHolder, DelegateItem>)
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        DaggerStreamComponent.factory().create(
            requireContext().getAppComponent(),
            StreamModule()
        ).inject(this)
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

    override fun render(state: StreamState) {
        hideAll()
        when (state) {
            is StreamState.Loading -> {
                loadingState.isVisible = true
            }
            is StreamState.Error -> {
                errorState.isVisible = true
            }
            is StreamState.Data -> {
                channelRecyclerView.isVisible = state.channels.isNotEmpty()
                adapter.submitList(state.channels)
            }
        }
    }

    private fun hideAll() {
        loadingState.isVisible = false
        errorState.isVisible = false
        channelRecyclerView.isVisible = false
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
