package kz.tinkoff.homework_2.presentation.channels.list

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import kz.tinkoff.core.adapter.AdapterDelegate
import kz.tinkoff.core.adapter.DelegateItem
import kz.tinkoff.core.adapter.MainAdapter
import kz.tinkoff.coreui.ScreenState
import kz.tinkoff.coreui.ext.hide
import kz.tinkoff.coreui.ext.show
import kz.tinkoff.homework_2.databinding.FragmentChannelListBinding
import kz.tinkoff.homework_2.presentation.channels.SearchEditTextController
import kz.tinkoff.homework_2.presentation.delegates.channels.ChannelDelegate
import kz.tinkoff.homework_2.presentation.delegates.channels.ChannelDelegateItem
import org.koin.androidx.viewmodel.ext.android.viewModel

class ChannelsListFragment : Fragment() {
    private var _binding: FragmentChannelListBinding? = null
    private val binding get() = _binding!!

    private val viewModel: ChannelsListViewModel by viewModel()

    private val channelRecyclerView: RecyclerView get() = binding.recyclerChannel
    private val errorState: ViewGroup get() = binding.errorState
    private val loadingState: ViewGroup get() = binding.loadingState

    private val delegate: ChannelDelegate by lazy(LazyThreadSafetyMode.NONE) {
        ChannelDelegate(listener = viewModel::navigateToMessageScreen)
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

        viewModel.channels
            .flowWithLifecycle(lifecycle)
            .onEach(::render)
            .launchIn(lifecycleScope)

        (parentFragment as SearchEditTextController).searchEditText { searchText ->
            search(searchText)
        }

        return binding.root
    }


    private fun render(state: ScreenState<List<ChannelDelegateItem>>) {
        when (state) {
            is ScreenState.Loading -> {
                hideAll()
                loadingState.show()
            }
            is ScreenState.Error -> {
                hideAll()
                errorState.show()
            }
            is ScreenState.Data -> {
                hideAll()
                channelRecyclerView.show()

                adapter.submitList(state.data)
            }
        }
    }

    fun search(searchText: String) {
        if (searchText.isNotEmpty()) {
            lifecycleScope.launch {
                searchText.let { query -> viewModel.searchQueryState.emit(query) }
            }
        } else {
            viewModel.getAllChannelsFromCashed()
        }
    }

    fun getAll() {
        viewModel.getAllChannelsFromCashed()
    }

    private fun hideAll() {
        loadingState.hide()
        channelRecyclerView.hide()
        errorState.hide()
    }


    companion object {
        fun getINSTANCE(): ChannelsListFragment {
            return ChannelsListFragment()
        }
    }
}