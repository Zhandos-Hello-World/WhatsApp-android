package kz.tinkoff.homework_2.presentation.streams

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.github.terrakok.cicerone.Router
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import javax.inject.Inject
import kz.tinkoff.homework_2.R
import kz.tinkoff.homework_2.databinding.FragmentChannelsBinding
import kz.tinkoff.homework_2.di_dagger.stream.DaggerStreamComponent
import kz.tinkoff.homework_2.getAppComponent
import kz.tinkoff.homework_2.navigation.Screens
import kz.tinkoff.homework_2.presentation.streams.StreamsListCollectionAdapter.Companion.ALL_STREAMS
import kz.tinkoff.homework_2.presentation.streams.StreamsListCollectionAdapter.Companion.SUBSCRIBED
import kz.tinkoff.homework_2.presentation.streams.list.StreamsListArgs
import kz.tinkoff.homework_2.presentation.streams.list.StreamsListFragment

class StreamsContainerFragment : Fragment(), SearchEditTextController {
    private var _binding: FragmentChannelsBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var router: Router

    private var adapter: StreamsListCollectionAdapter? = null
    private var listOfFragments: List<Fragment>? = null

    private var listener: (String) -> Unit = { }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        DaggerStreamComponent.builder()
            .appComponent(requireContext().getAppComponent())
            .build().inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentChannelsBinding.inflate(inflater, container, false)

        binding.createStreamBtn.setOnClickListener {
            router.navigateTo(Screens.CreateStreamScreen())
        }

        configureSearchBar()
        configureViewPagers()
        configureTabLayout()
        return binding.root
    }

    private fun configureSearchBar() {
        binding.apply {
            searchBar.setHint(getString(kz.tinkoff.core.R.string.search_with_three_dots))
            searchBar.doOnTextChanged { searchText ->
                listener.invoke(searchText)
            }
        }
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
                binding.searchBar.setText("")
                binding.searchBar.hideKeyboard()
                listOfFragments?.forEach { fragment ->
                    (fragment as? StreamsListFragment)?.getAll()
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {}

            override fun onTabReselected(tab: TabLayout.Tab?) {}
        })
    }

    private fun configureViewPagers() {
        val listOfFragments =
            mutableListOf<Fragment>(
                StreamsListFragment.getINSTANCE(
                    StreamsListArgs(requestType = StreamsListArgs.StreamRequest.SUBSCRIBED)
                ),
                StreamsListFragment.getINSTANCE(
                    StreamsListArgs(requestType = StreamsListArgs.StreamRequest.ALL_STREAMS)
                ),
            )

        adapter = StreamsListCollectionAdapter(this)
        binding.viewPager.adapter = adapter
        adapter?.update(listOfFragments)
    }

    override fun onDestroyView() {
        listOfFragments = null
        super.onDestroyView()
    }

    override fun searchEditText(searchListener: (String) -> Unit) {
        listener = searchListener
    }
}
