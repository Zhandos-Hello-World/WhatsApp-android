package kz.tinkoff.homework_2.presentation.channels.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import kz.tinkoff.core.adapter.AdapterDelegate
import kz.tinkoff.core.adapter.DelegateItem
import kz.tinkoff.core.adapter.MainAdapter
import kz.tinkoff.homework_2.databinding.FragmentChannelListBinding
import kz.tinkoff.homework_2.presentation.delegates.channels.ChannelDelegate
import org.koin.androidx.viewmodel.ext.android.viewModel

class ChannelsListFragment : Fragment() {
    private var _binding: FragmentChannelListBinding? = null
    private val binding get() = _binding!!

    private val viewModel: ChannelsListViewModel by viewModel()

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

        viewModel.channels.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }
        return binding.root
    }


    companion object {
        fun getINSTANCE(): ChannelsListFragment {
            return ChannelsListFragment()
        }
    }
}