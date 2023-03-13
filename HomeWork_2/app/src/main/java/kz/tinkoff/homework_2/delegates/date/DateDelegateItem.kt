package kz.tinkoff.homework_2.delegates.date

import kz.tinkoff.core.adapter.DelegateItem

class DateDelegateItem(
    val id: Int,
    private val value: DateModel,
): DelegateItem {

    override fun content(): Any = value

    override fun id(): Int = id

    override fun compareToOther(other: DelegateItem): Boolean {
        return (other as DateDelegateItem).value == content()
    }
}