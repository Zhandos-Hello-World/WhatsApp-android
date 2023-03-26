package kz.tinkoff.homework_2.presentation.channels

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayoutMediator
import kz.tinkoff.homework_2.R
import kz.tinkoff.homework_2.databinding.FragmentChannelsBinding
import kz.tinkoff.homework_2.presentation.channels.ChannelsCollectionAdapter.Companion.ALL_STREAMS
import kz.tinkoff.homework_2.presentation.channels.ChannelsCollectionAdapter.Companion.SUBSCRIBED

class ChannelsFragment : Fragment() {
    private var _binding: FragmentChannelsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentChannelsBinding.inflate(inflater, container, false)

        binding.searchBar.setHint(getString(kz.tinkoff.core.R.string.search_with_three_dots))

        binding.viewPager.adapter = ChannelsCollectionAdapter(this)

        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            tab.text = when (position) {
                SUBSCRIBED -> getString(R.string.subscribed)
                ALL_STREAMS -> getString(R.string.all_streams)
                else -> ""
            }
        }.attach()
        return binding.root
    }


}