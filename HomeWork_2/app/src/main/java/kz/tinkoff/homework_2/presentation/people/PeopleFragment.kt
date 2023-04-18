package kz.tinkoff.homework_2.presentation.people

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import kz.tinkoff.core.adapter.AdapterDelegate
import kz.tinkoff.core.adapter.DelegateItem
import kz.tinkoff.core.adapter.MainAdapter
import kz.tinkoff.core.utils.lazyUnsafe
import kz.tinkoff.homework_2.databinding.FragmentPeopleBinding
import kz.tinkoff.homework_2.di_dagger.getApplication
import kz.tinkoff.homework_2.presentation.delegates.person.PersonDelegate
import kz.tinkoff.homework_2.presentation.people.elm.PeopleEffect
import kz.tinkoff.homework_2.presentation.people.elm.PeopleEvent
import kz.tinkoff.homework_2.presentation.people.elm.PeopleState
import kz.tinkoff.homework_2.presentation.people.elm.PeopleStoreFactory
import org.koin.android.ext.android.inject
import vivid.money.elmslie.android.base.ElmFragment
import vivid.money.elmslie.android.storeholder.LifecycleAwareStoreHolder

class PeopleFragment : ElmFragment<PeopleEvent, PeopleEffect, PeopleState>() {
    private var _binding: FragmentPeopleBinding? = null
    private val binding get() = _binding!!

    override val storeHolder by lazyUnsafe {
        LifecycleAwareStoreHolder(lifecycle) {
            getApplication().peopleComponent.getPeopleStore().provide()
        }
    }

    override val initEvent: PeopleEvent = PeopleEvent.Ui.LoadPeople

    val delegate = PersonDelegate { }
    private val adapter: MainAdapter by lazyUnsafe {
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

        binding.apply {
            searchEditText.setHint(getString(kz.tinkoff.core.R.string.users_with_three_dots))

            searchEditText.doOnTextChanged { searchText ->
                if (searchText.isNotEmpty()) {
                    searchText.let { query ->
                        store.accept(PeopleEvent.Ui.SearchPerson(query))
                    }
                } else {
                    store.accept(PeopleEvent.Ui.LoadPeople)
                }
            }
            usersRecyclerView.adapter = adapter

            reloadRequestBtn.setOnClickListener {
                store.accept(PeopleEvent.Ui.LoadPeople)
            }
        }

        return binding.root
    }

    override fun render(state: PeopleState) {
        hideAll()
        when (state) {
            is PeopleState.Data -> {
                binding.usersRecyclerView.isVisible = true
                adapter.submitList(state.peopleDvo)
            }
            is PeopleState.Loading -> {
                binding.loadingState.isVisible = true
            }
            is PeopleState.Error -> {
                binding.errorState.isVisible = true
            }
        }
    }

    private fun hideAll() {
        binding.apply {
            usersRecyclerView.isVisible = false
            loadingState.isVisible = false
            errorState.isVisible = false
        }
    }
}
