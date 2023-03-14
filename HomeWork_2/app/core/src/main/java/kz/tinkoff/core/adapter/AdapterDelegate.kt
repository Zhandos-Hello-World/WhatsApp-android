package kz.tinkoff.core.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

interface AdapterDelegate<VH: RecyclerView.ViewHolder, M> {
    fun onCreateViewHolder(parent: ViewGroup): VH
    fun onBindViewHolder(holder: VH, item: DelegateItem<M>, position: Int)
    fun isOfViewType(item: DelegateItem<M>): Boolean
}