package kz.tinkoff.homework_2.di

import kz.tinkoff.homework_2.presentation.people.PeopleViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {

    viewModel { PeopleViewModel(
        repository = get(),
        personDvoMapper = get()
    )}
}