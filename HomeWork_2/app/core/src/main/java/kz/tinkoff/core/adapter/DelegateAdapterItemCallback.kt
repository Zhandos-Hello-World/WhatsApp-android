package kz.tinkoff.core.adapter

import androidx.recyclerview.widget.DiffUtil

class DelegateAdapterItemCallback : DiffUtil.ItemCallback<DelegateItem<Any>>() {
    override fun areItemsTheSame(oldItem: DelegateItem<Any>, newItem: DelegateItem<Any>): Boolean {
        return oldItem::class == newItem::class && oldItem.id() == newItem.id()
    }

    override fun areContentsTheSame(oldItem: DelegateItem<Any>, newItem: DelegateItem<Any>): Boolean {
        return oldItem.compareToOther(newItem)
    }
}
