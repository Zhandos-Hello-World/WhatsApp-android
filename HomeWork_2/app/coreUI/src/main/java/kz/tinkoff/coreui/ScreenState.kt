package kz.tinkoff.coreui

sealed class ScreenState<out T : Any> {
    object Loading : ScreenState<Nothing>()
    data class Data<out T : Any>(val data: T) : ScreenState<T>()
    object Error : ScreenState<Nothing>()
}