package kz.tinkoff.core.adapter

abstract class DelegateItem {

    abstract fun content(): Any

    abstract fun id(): Int

    fun areSame(newItem: DelegateItem): Boolean {
        return this.javaClass == newItem.javaClass && id() == newItem.id()
    }

    fun areContentsTheSame(newItem: DelegateItem): Boolean {
        return id() == newItem.id()
    }
}
