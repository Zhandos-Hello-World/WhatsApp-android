package kz.tinkoff.homework_2.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class BaseResponse(
    @SerialName("msg")
    val msg: String? = null,
    @SerialName("result")
    val result: String? = null,
) {

    fun isSuccess(): Boolean {
        return result.orEmpty() == SUCCESS
    }

    companion object {
        const val SUCCESS = "success"
    }
}
