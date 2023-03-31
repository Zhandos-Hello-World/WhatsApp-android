package kz.tinkoff.homework_2.presentation.channels

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import kz.tinkoff.coreui.custom.viewgroup.CustomSearchEditText
import kz.tinkoff.homework_2.R
import kz.tinkoff.homework_2.databinding.FragmentChannelsBinding
import kz.tinkoff.homework_2.presentation.channels.ChannelsCollectionAdapter.Companion.ALL_STREAMS
import kz.tinkoff.homework_2.presentation.channels.ChannelsCollectionAdapter.Companion.SUBSCRIBED
import kz.tinkoff.homework_2.presentation.channels.list.ChannelsListFragment

class ChannelsFragment : Fragment(), SearchEditTextController {
    private var _binding: FragmentChannelsBinding? = null
    private val binding get() = _binding!!

    private var adapter: ChannelsCollectionAdapter? = null
    private var listOfFragments: List<Fragment>? = null

    private val searchEditText: CustomSearchEditText get() = binding.searchBar

    private var listener: (String) -> Unit = { }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentChannelsBinding.inflate(inflater, container, false)
        binding.searchBar.setHint(getString(kz.tinkoff.core.R.string.search_with_three_dots))

        searchEditText.doOnTextChanged { searchText ->
            listener.invoke(searchText)
        }

        val listOfFragments =
            mutableListOf<Fragment>(ChannelsListFragment.getINSTANCE(), Fragment())

        adapter = ChannelsCollectionAdapter(this)
        binding.viewPager.adapter = adapter

        adapter?.update(listOfFragments)

        configureTabLayout()
        return binding.root
    }


    private fun configureTabLayout() {

        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            tab.text = when (position) {
                SUBSCRIBED -> getString(R.string.subscribed)
                ALL_STREAMS -> getString(R.string.all_streams)
                else -> ""
            }
        }.attach()

        binding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                searchEditText.setText("")
                searchEditText.hideKeyboard()
                listOfFragments?.forEach { fragment ->
                    (fragment as? ChannelsListFragment)?.getAll()
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
            }
        })
    }

    override fun onDestroyView() {
        listOfFragments = null
        super.onDestroyView()
    }

    override fun searchEditText(searchListener: (String) -> Unit) {
        listener = searchListener
    }

}