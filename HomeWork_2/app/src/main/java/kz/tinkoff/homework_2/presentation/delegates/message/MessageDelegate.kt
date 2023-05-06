package kz.tinkoff.homework_2.presentation.delegates.message

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kz.tinkoff.core.adapter.AdapterDelegate
import kz.tinkoff.core.adapter.DelegateItem
import kz.tinkoff.coreui.custom.dvo.MessageDvo
import kz.tinkoff.homework_2.databinding.ItemMessageBinding

class MessageDelegate(private val listener: MessageAdapterListener) :
    AdapterDelegate<MessageDelegate.ViewHolder, MessageDelegateItem> {

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
        item: MessageDelegateItem,
        position: Int,
    ) {
        holder.bind(item.content(), position)
    }

    override fun isOfViewType(item: DelegateItem): Boolean =
        item is MessageDelegateItem

    inner class ViewHolder(private val binding: ItemMessageBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(model: MessageDvo, position: Int) {
            with(binding.message) {

                setMessageDvo(model)

                setEmojiClickListener { messageDvo, reactionViewItem ->
                    listener.setEmojiClickListener(
                        position = position,
                        viewItem = reactionViewItem
                    )
                }

                addReactionClickListener {
                    listener.addReactionClickListener(position)
                }
            }
        }
    }
}
