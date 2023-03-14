package kz.tinkoff.homework_2.delegates.message

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kz.tinkoff.core.adapter.AdapterDelegate
import kz.tinkoff.core.adapter.DelegateItem
import kz.tinkoff.homework_2.databinding.ItemMessageBinding
import kz.tinkoff.homework_2.delegates.mapper.MessageDvoMapper

class MessageDelegate(private val listener: MessageAdapterListener) :
    AdapterDelegate<MessageDelegate.ViewHolder, MessageModel> {

    override fun onCreateViewHolder(parent: ViewGroup): ViewHolder =
        ViewHolder(
            ItemMessageBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(
        holder: ViewHolder,
        item: DelegateItem<MessageModel>,
        position: Int,
    ) {
        holder.bind(item.content())
    }

    override fun isOfViewType(item: DelegateItem<MessageModel>): Boolean =
        item is MessageDelegateItem

    inner class ViewHolder(private val binding: ItemMessageBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(model: MessageModel) {
            with(binding.message) {

                setMessageDvo(MessageDvoMapper().map(model))

                setEmojiClickListener { messageDvo, reactionViewItem ->
                    listener.setEmojiClickListener(
                        model = MessageDvoMapper().toMessageModel(
                            from = messageDvo
                        ),
                        viewItem = reactionViewItem
                    )
                }

                addReactionClickListener {
                    listener.addReactionClickListener(model)
                }
            }
        }
    }
}