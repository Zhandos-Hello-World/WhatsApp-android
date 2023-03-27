package kz.tinkoff.coreui

sealed class ScreenState<out T : Any> {
    object Loading : ScreenState<Nothing>()
    data class Data<out T : Any>(val data: T) : ScreenState<T>()
    data class Error(val error: Throwable) : ScreenState<Nothing>()
}