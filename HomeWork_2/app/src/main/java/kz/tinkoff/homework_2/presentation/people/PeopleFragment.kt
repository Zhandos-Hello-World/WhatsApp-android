package kz.tinkoff.homework_2.presentation.people

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import kz.tinkoff.core.adapter.AdapterDelegate
import kz.tinkoff.core.adapter.DelegateItem
import kz.tinkoff.core.adapter.MainAdapter
import kz.tinkoff.coreui.custom.viewgroup.CustomSearchEditText
import kz.tinkoff.homework_2.databinding.FragmentPeopleBinding
import kz.tinkoff.homework_2.presentation.delegates.person.PersonDelegate
import kz.tinkoff.homework_2.presentation.people.elm.PeopleEffect
import kz.tinkoff.homework_2.presentation.people.elm.PeopleEvent
import kz.tinkoff.homework_2.presentation.people.elm.PeopleState
import kz.tinkoff.homework_2.presentation.people.elm.PeopleStoreFactory
import org.koin.android.ext.android.inject
import vivid.money.elmslie.android.base.ElmFragment

class PeopleFragment : ElmFragment<PeopleEvent, PeopleEffect, PeopleState>() {
    private var _binding: FragmentPeopleBinding? = null
    private val binding get() = _binding!!

    private val searchEditText: CustomSearchEditText get() = binding.searchEditText
    private val usersRecyclerView: RecyclerView get() = binding.usersRecyclerView
    private val errorState: ViewGroup get() = binding.errorState
    private val loadingState: ViewGroup get() = binding.loadingState
    private val reloadRequestBtn: Button get() = binding.reloadRequestBtn

    private val peopleStoreFactory: PeopleStoreFactory by inject()

    override val initEvent: PeopleEvent = PeopleEvent.Ui.LoadPeople

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


        searchEditText.doOnTextChanged { searchText ->
            if (searchText.isNotEmpty()) {
                searchText.let { query ->
                    store.accept(PeopleEvent.Ui.SearchPerson(query))
                }
            } else {
                store.accept(PeopleEvent.Ui.LoadPeople)
            }
        }

        reloadRequestBtn.setOnClickListener {
            store.accept(PeopleEvent.Ui.LoadPeople)
        }

        return binding.root
    }

    // Почему Deprecated метод?
    override fun createStore() = peopleStoreFactory.provide()

    override fun render(state: PeopleState) {
        loadingState.isVisible = state.isLoading
        errorState.isVisible = state.error

        usersRecyclerView.isVisible = state.peopleDvo.isNotEmpty()
        adapter.submitList(state.peopleDvo)
    }

}
