package kz.tinkoff.core.utils

fun <T>lazyUnsafe(initializer: () -> T) = lazy(LazyThreadSafetyMode.NONE, initializer)
