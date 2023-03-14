package kz.tinkoff.homework_2.delegates.utils

import kz.tinkoff.core.adapter.DelegateItem
import kz.tinkoff.homework_2.delegates.date.DateDelegateItem
import kz.tinkoff.homework_2.delegates.date.DateModel
import kz.tinkoff.homework_2.delegates.message.MessageDelegateItem
import kz.tinkoff.homework_2.delegates.message.MessageModel


fun List<MessageModel>.concatenateWithDate(dates: List<DateModel>): List<DelegateItem<Any>> {
    val delegateItemList: MutableList<DelegateItem<Any>> = mutableListOf()

    dates.forEach { dateModel ->
        delegateItemList.add(
            DateDelegateItem(value = dateModel) as DelegateItem<Any>
        )

        val date = dateModel.date
        val allDayExpenses = this.filter { message ->
            message.date == date
        }
        allDayExpenses.forEach { model ->
            delegateItemList.add(
                MessageDelegateItem(
                    id = model.id,
                    value = model,
                ) as DelegateItem<Any>
            )
        }
    }
    return delegateItemList
}
