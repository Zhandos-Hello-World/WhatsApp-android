package kz.tinkoff.homework_2.delegates.date

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kz.tinkoff.homework_2.databinding.ItemDateBinding
import kz.tinkoff.core.adapter.AdapterDelegate
import kz.tinkoff.core.adapter.DelegateItem

class DateDelegate: AdapterDelegate<DateDelegate.ViewHolder, DateModel> {

    override fun onCreateViewHolder(parent: ViewGroup): ViewHolder =
        ViewHolder(
            ItemDateBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(
        holder: ViewHolder,
        item: DelegateItem<DateModel>,
        position: Int
    ) {
        holder.bind(item.content())
    }

    override fun isOfViewType(item: DelegateItem<DateModel>): Boolean = item is DateDelegateItem

    class ViewHolder(private val binding: ItemDateBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(model: DateModel) {
            with(binding) {
                dateText.dateText = model.date
            }
        }
    }
}