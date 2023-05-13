package kz.tinkoff.homework_2.presentation.streams.list

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
import kz.tinkoff.homework_2.databinding.FragmentStreamListBinding
import kz.tinkoff.homework_2.di_dagger.stream.DaggerStreamComponent
import kz.tinkoff.homework_2.di_dagger.stream.modules.StreamDataModule
import kz.tinkoff.homework_2.di_dagger.stream.modules.StreamNetworkModule
import kz.tinkoff.homework_2.getAppComponent
import kz.tinkoff.homework_2.presentation.delegates.channels.StreamDelegate
import kz.tinkoff.homework_2.presentation.dvo.StreamDvo
import kz.tinkoff.homework_2.presentation.message.MessageArgs
import kz.tinkoff.homework_2.presentation.streams.SearchEditTextController
import kz.tinkoff.homework_2.presentation.streams.elm.StreamEffect
import kz.tinkoff.homework_2.presentation.streams.elm.StreamEvent
import kz.tinkoff.homework_2.presentation.streams.elm.StreamState
import kz.tinkoff.homework_2.presentation.streams.elm.StreamStoreFactory
import vivid.money.elmslie.android.base.ElmFragment
import vivid.money.elmslie.android.storeholder.LifecycleAwareStoreHolder

class StreamsListFragment(private val args: StreamsListArgs) :
    ElmFragment<StreamEvent, StreamEffect, StreamState>() {
    private var _binding: FragmentStreamListBinding? = null
    private val binding get() = _binding!!

    override val initEvent: StreamEvent = StreamEvent.Ui.LoadStream(args)

    @Inject
    lateinit var storeFactory: StreamStoreFactory

    override val storeHolder by lazyUnsafe {
        LifecycleAwareStoreHolder(lifecycle) {
            storeFactory.provide()
        }
    }

    private val delegate: StreamDelegate by lazyUnsafe {
        StreamDelegate(
            topicOnClickListener = ::navigateToMessage,
            streamOnClickListener = ::getTopicsById
        )
    }

    private val adapter: MainAdapter by lazyUnsafe {
        MainAdapter().apply {
            addDelegate(delegate as AdapterDelegate<RecyclerView.ViewHolder, DelegateItem>)
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        DaggerStreamComponent.builder()
            .streamDataModule(StreamDataModule())
            .streamNetworkModule(StreamNetworkModule())
            .appComponent(requireContext().getAppComponent())
            .build().inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentStreamListBinding.inflate(inflater, container, false)
        binding.streamRecycler.adapter = adapter

        (parentFragment as SearchEditTextController).searchEditText { searchText ->
            if (searchText.isNotEmpty()) {
                store.accept(StreamEvent.Ui.SearchStream(searchText))
            } else {
                getAll()
            }
        }

        return binding.root
    }

    override fun render(state: StreamState) {
        hideAll()
        binding.apply {
            when (state) {
                is StreamState.Loading -> {
                    loadingState.isVisible = true
                }
                is StreamState.Error -> {
                    errorState.isVisible = true
                }
                is StreamState.Data -> {
                    streamRecycler.isVisible = state.channels.isNotEmpty()
                    adapter.submitList(state.channels)
                }
                is StreamState.UpdatePosition -> {
                    streamRecycler.isVisible = true
                    adapter.notifyItemChanged(state.position)
                }
            }
        }
    }

    override fun handleEffect(effect: StreamEffect) {
        return when (effect) {
            is StreamEffect.CreateTopic -> {
                createDialog(effect.dvo, effect.position)
            }
        }
    }

    private fun hideAll() {
        binding.apply {
            loadingState.isVisible = false
            errorState.isVisible = false
            streamRecycler.isVisible = false
        }
    }

    private fun getTopicsById(streamId: Int) {
        store.accept(StreamEvent.Ui.LoadTopic(streamId))
    }

    private fun navigateToMessage(args: MessageArgs) {
        store.accept(StreamEvent.Ui.NavigateToMessage(args))
    }

    private fun createDialog(dvo: StreamDvo, position: Int) {
        store.accept(
            StreamEvent.Ui.NavigateToCreateTopic(
                dvo = dvo,
                position = position
            )
        )
    }

    fun getAll() {
        store.accept(StreamEvent.Ui.LoadStream(args))
    }


    companion object {
        fun getINSTANCE(args: StreamsListArgs): StreamsListFragment {
            return StreamsListFragment(args)
        }
    }
}
