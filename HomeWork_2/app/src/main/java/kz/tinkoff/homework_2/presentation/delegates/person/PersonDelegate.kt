package kz.tinkoff.homework_2.presentation.delegates.person

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kz.tinkoff.core.adapter.AdapterDelegate
import kz.tinkoff.core.adapter.DelegateItem
import kz.tinkoff.homework_2.databinding.ItemPersonBinding

class PersonDelegate(private val personClickListener: (id: Int) -> Unit) :
    AdapterDelegate<PersonDelegate.ViewHolder, PersonDelegateItem> {

    override fun onCreateViewHolder(parent: ViewGroup): ViewHolder =
        ViewHolder(
            ItemPersonBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(
        holder: ViewHolder,
        item: PersonDelegateItem,
        position: Int,
    ) {
        holder.bind(item.content())
    }

    override fun isOfViewType(item: DelegateItem): Boolean =
        item is PersonDelegateItem

    inner class ViewHolder(private val binding: ItemPersonBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(model: PersonModel) {
            binding.apply {
                avatar.isOnline = model.isOnline
                fullNameText.text = model.fullName
                emailText.text = model.email

                root.setOnClickListener {
                    personClickListener.invoke(model.id)
                }
            }
        }
    }
}