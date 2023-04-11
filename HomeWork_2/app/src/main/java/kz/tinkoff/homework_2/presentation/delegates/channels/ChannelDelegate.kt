package kz.tinkoff.homework_2.presentation.delegates.channels

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import kz.tinkoff.core.adapter.AdapterDelegate
import kz.tinkoff.core.adapter.DelegateItem
import kz.tinkoff.homework_2.databinding.ItemChannelBinding
import kz.tinkoff.homework_2.databinding.ItemTopicBinding
import kz.tinkoff.homework_2.presentation.dvo.StreamDvo
import kz.tinkoff.homework_2.presentation.message.MessageArgs

class ChannelDelegate(private val listener: (MessageArgs) -> Unit) :
    AdapterDelegate<ChannelDelegate.ViewHolder, ChannelDelegateItem> {

    override fun onCreateViewHolder(parent: ViewGroup): ViewHolder =
        ViewHolder(ItemChannelBinding.inflate(LayoutInflater.from(parent.context), parent, false))

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
                binding.expandableItems.isVisible = field
            }

        fun bind(model: StreamDvo) {
            binding.channelBanner.setOnClickListener {
                isExpanded = !isExpanded
            }
            binding.channelNameText.text = model.name
            addTopics(model.topicsDvo, model)
        }

        private fun addTopics(topics: List<StreamDvo.TopicDvo>, model: StreamDvo) {
            topics.forEachIndexed { index, topicDvo ->
                val context = binding.root.context
                val topicBinding =
                    ItemTopicBinding.inflate(LayoutInflater.from(context), null, false)

                topicBinding.apply {
                    topicName.text = topicDvo.name

                    val color = if (index % 2 != 0) kz.tinkoff.coreui.R.color.orange else kz.tinkoff.coreui.R.color.green_2
                    topic.setBackgroundColor(ContextCompat.getColor(context, color))
                    root.setOnClickListener { listener.invoke(
                        MessageArgs(
                            streamId = model.id,
                            stream = model.name,
                            topic = topicDvo.name
                        )
                    ) }
                }

                binding.expandableItems.addView(topicBinding.root)
            }
        }
    }
}