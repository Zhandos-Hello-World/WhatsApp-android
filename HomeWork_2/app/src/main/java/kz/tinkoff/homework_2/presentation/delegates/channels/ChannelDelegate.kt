package kz.tinkoff.homework_2.presentation.delegates.channels

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kz.tinkoff.core.adapter.AdapterDelegate
import kz.tinkoff.core.adapter.DelegateItem
import kz.tinkoff.homework_2.databinding.ItemChannelBinding

class ChannelDelegate(private val listener: () -> Unit) : AdapterDelegate<ChannelDelegate.ViewHolder, ChannelDelegateItem> {

    override fun onCreateViewHolder(parent: ViewGroup): ViewHolder =
        ViewHolder(
            ItemChannelBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(
        holder: ViewHolder,
        item: ChannelDelegateItem,
        position: Int,
    ) {
        holder.bind(item.content())
    }

    override fun isOfViewType(item: DelegateItem): Boolean = item is ChannelDelegateItem

    inner class ViewHolder(private val binding: ItemChannelBinding) :
        RecyclerView.ViewHolder(binding.root) {
        private var isExpanded = false
            set(value) {
                field = value
                binding.expandableItems.visibility = if (field) View.VISIBLE else View.GONE
            }

        fun bind(model: ChannelDvo) {
            binding.channelBanner.setOnClickListener {
                isExpanded = !isExpanded
            }
            binding.channelNameText.text = model.name

            binding.testing.setOnClickListener { listener.invoke() }
            binding.brush.setOnClickListener { listener.invoke() }

        }
    }
}