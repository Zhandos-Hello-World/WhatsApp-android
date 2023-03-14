package kz.tinkoff.core.adapter

import androidx.recyclerview.widget.DiffUtil

class DelegateAdapterItemCallback : DiffUtil.ItemCallback<DelegateItem>() {
    override fun areItemsTheSame(oldItem: DelegateItem, newItem: DelegateItem): Boolean {
        return oldItem.areSame(newItem)
    }

    override fun areContentsTheSame(oldItem: DelegateItem, newItem: DelegateItem): Boolean {
        return oldItem.areContentsTheSame(newItem)
    }
}
