package kz.tinkoff.core.adapter

interface DelegateItem {

    fun content(): Any

    fun id(): Int

    fun areSame(newItem: DelegateItem): Boolean {
        return this.javaClass == newItem.javaClass && id() == newItem.id()
    }

    fun areContentsTheSame(newItem: DelegateItem): Boolean {
        return id() == newItem.id()
    }
}
