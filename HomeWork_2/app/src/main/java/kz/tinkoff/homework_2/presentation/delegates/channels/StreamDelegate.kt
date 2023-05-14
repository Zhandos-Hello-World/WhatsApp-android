package kz.tinkoff.homework_2.presentation.delegates.channels

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import kz.tinkoff.core.adapter.AdapterDelegate
import kz.tinkoff.core.adapter.DelegateItem
import kz.tinkoff.homework_2.databinding.ItemAppendTopicBinding
import kz.tinkoff.homework_2.databinding.ItemChannelBinding
import kz.tinkoff.homework_2.databinding.ItemTopicBinding
import kz.tinkoff.homework_2.presentation.dvo.StreamDvo
import kz.tinkoff.homework_2.presentation.message.MessageArgs

class StreamDelegate(
    private val streamOnClickListener: (Int) -> Unit,
    private val topicOnClickListener: (MessageArgs) -> Unit,
    private val addTopicClickListener: (StreamDvo) -> Unit
) :
    AdapterDelegate<StreamDelegate.ViewHolder, StreamDelegateItem> {

    override fun onCreateViewHolder(parent: ViewGroup): ViewHolder =
        ViewHolder(ItemChannelBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(
        holder: ViewHolder,
        item: StreamDelegateItem,
        position: Int,
    ) {
        holder.bind(item.content())
    }

    override fun isOfViewType(item: DelegateItem): Boolean = item is StreamDelegateItem

    inner class ViewHolder(private val binding: ItemChannelBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(model: StreamDvo) {
            binding.apply {
                channelNameText.text = model.name
                expandableItems.isVisible = model.expanded

                arrow.rotation = if(model.expanded) 180F else 0F
                channelBanner.setOnClickListener {
                    streamOnClickListener.invoke(model.id)
                }
            }

            setTopics(model.topicsDvo, model)
        }

        private fun setTopics(topics: List<StreamDvo.TopicDvo>, model: StreamDvo) {
            binding.expandableItems.removeAllViewsInLayout()

            topics.forEach { topicDvo ->
                addTopicOn(
                    viewGroup = binding.expandableItems,
                    topicDvo = topicDvo,
                    onClickListener = {
                        topicOnClickListener.invoke(
                            MessageArgs(
                                streamId = model.id,
                                stream = model.name,
                                topic = topicDvo.name
                            )
                        )
                    }
                )
            }
            addAppendTopicViewOn(
                binding.expandableItems,
                onClickListener = {
                    addTopicClickListener.invoke(model)
                }
            )
        }

        private fun addTopicOn(
            viewGroup: ViewGroup,
            topicDvo: StreamDvo.TopicDvo,
            onClickListener: () -> Unit,
        ) {
            val context = viewGroup.context
            val topicBinding =
                ItemTopicBinding.inflate(LayoutInflater.from(context), null, false)

            topicBinding.apply {
                topicName.text = topicDvo.name
                topic.setBackgroundColor(ContextCompat.getColor(context, topicDvo.color))
                root.setOnClickListener { onClickListener.invoke() }
            }
            viewGroup.addView(topicBinding.root)
        }

        private fun addAppendTopicViewOn(
            viewGroup: ViewGroup,
            onClickListener: () -> Unit,
        ) {
            val context = viewGroup.context
            val topicBinding =
                ItemAppendTopicBinding.inflate(LayoutInflater.from(context), null, false)

            topicBinding.apply {
                root.setOnClickListener { onClickListener.invoke() }
            }
            viewGroup.addView(topicBinding.root)
        }
    }
}
