package kz.tinkoff.homework_2.presentation.delegates.person

import kz.tinkoff.core.adapter.DelegateItem

class PersonDelegateItem(
    val id: Int,
    private val value: PersonModel,
) : DelegateItem {

    override fun content(): PersonModel = value

    override fun id(): Int = id

}