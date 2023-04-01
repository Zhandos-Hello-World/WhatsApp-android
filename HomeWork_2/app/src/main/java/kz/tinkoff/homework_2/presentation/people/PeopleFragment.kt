package kz.tinkoff.homework_2.presentation.people

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
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
import kz.tinkoff.coreui.custom.viewgroup.CustomSearchEditText
import kz.tinkoff.coreui.ext.hide
import kz.tinkoff.coreui.ext.show
import kz.tinkoff.homework_2.databinding.FragmentPeopleBinding
import kz.tinkoff.homework_2.presentation.delegates.person.PersonDelegate
import kz.tinkoff.homework_2.presentation.delegates.person.PersonDelegateItem
import org.koin.androidx.viewmodel.ext.android.viewModel

class PeopleFragment : Fragment() {
    private var _binding: FragmentPeopleBinding? = null
    private val binding get() = _binding!!

    private val viewModel: PeopleViewModel by viewModel()

    private val searchEditText: CustomSearchEditText get() = binding.searchEditText
    private val usersRecyclerView: RecyclerView get() = binding.usersRecyclerView
    private val errorState: ViewGroup get() = binding.errorState
    private val loadingState: ViewGroup get() = binding.loadingState
    private val reloadRequestBtn: Button get() = binding.reloadRequestBtn


    val delegate = PersonDelegate { }
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
        _binding = FragmentPeopleBinding.inflate(inflater, container, false)

        searchEditText.setHint(getString(kz.tinkoff.core.R.string.users_with_three_dots))
        usersRecyclerView.adapter = adapter

        viewModel.peopleList.flowWithLifecycle(lifecycle).onEach(::render).launchIn(lifecycleScope)

        searchEditText.doOnTextChanged { searchText ->
            if (searchText.isNotEmpty()) {
                lifecycleScope.launch {
                    searchText.let { query -> viewModel.searchQueryState.emit(query) }
                }
            } else {
                viewModel.getAllFromCache()
            }
        }

        reloadRequestBtn.setOnClickListener {
            viewModel.getAll()
        }


        return binding.root
    }


    private fun render(state: ScreenState<List<PersonDelegateItem>>) {
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
                usersRecyclerView.show()

                adapter.submitList(state.data)
            }
        }
    }

    private fun hideAll() {
        loadingState.hide()
        usersRecyclerView.hide()
        errorState.hide()

    }

}