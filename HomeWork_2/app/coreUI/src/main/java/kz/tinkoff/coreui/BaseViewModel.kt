package kz.tinkoff.coreui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.launch

abstract class BaseViewModel : ViewModel() {

    fun <T> networkRequest(
        request: suspend () -> T,
        onSuccess: suspend (T) -> Unit,
        onFail: (suspend (Throwable) -> Unit)? = null,
        onBeforeRequest: suspend () -> Unit = { },
        finally: () -> Unit = { },
    ) = viewModelScope.launch {
        onBeforeRequest()
        try {
            val response = request.invoke()
            onSuccess(response)
        } catch (ex: CancellationException) {
            throw ex
        } catch (ex: Exception) {
            onFail?.invoke(ex)
        } finally {
            finally()
        }
    }
}