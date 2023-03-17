package kz.tinkoff.core.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

interface AdapterDelegate<VH: RecyclerView.ViewHolder, D: DelegateItem> {
    fun onCreateViewHolder(parent: ViewGroup): VH
    fun onBindViewHolder(holder: VH, item: D, position: Int)
    fun isOfViewType(item: DelegateItem): Boolean
}