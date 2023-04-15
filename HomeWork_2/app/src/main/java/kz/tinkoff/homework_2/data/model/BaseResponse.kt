package kz.tinkoff.homework_2.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

// Зачем Parcelable?
@Parcelize
data class BaseResponse(
    val msg: String? = null,
    val result: String? = null
) : Parcelable
