package kz.tinkoff.core

interface Mapper<T, R> {

    fun map(from: T): R
}