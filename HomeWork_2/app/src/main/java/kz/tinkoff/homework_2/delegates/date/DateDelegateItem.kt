package kz.tinkoff.homework_2.delegates.date

import kz.tinkoff.core.adapter.DelegateItem

class DateDelegateItem(private val value: DateModel): DelegateItem<DateModel> {

    override fun content(): DateModel = value

    override fun id(): Int = value.hashCode()

    override fun compareToOther(other: DelegateItem<DateModel>): Boolean {
        return other.content() == content()
    }
}