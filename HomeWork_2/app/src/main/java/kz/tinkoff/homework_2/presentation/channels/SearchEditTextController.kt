package kz.tinkoff.homework_2.presentation.channels

interface SearchEditTextController {

    fun searchEditText(searchListener: (String) -> Unit)
}