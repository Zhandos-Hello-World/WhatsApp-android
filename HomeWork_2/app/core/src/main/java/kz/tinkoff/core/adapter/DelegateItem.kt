package kz.tinkoff.core.adapter

interface DelegateItem<M> {
    fun content(): M
    fun id(): Int
    fun compareToOther(other: DelegateItem<M>): Boolean
}
