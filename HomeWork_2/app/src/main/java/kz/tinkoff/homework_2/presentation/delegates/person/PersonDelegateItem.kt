package kz.tinkoff.homework_2.presentation.delegates.person

import kz.tinkoff.core.adapter.DelegateItem

class PersonDelegateItem(
    val id: Int,
    private val value: PersonDvo,
) : DelegateItem {

    override fun content(): PersonDvo = value

    override fun id(): Int = id

}