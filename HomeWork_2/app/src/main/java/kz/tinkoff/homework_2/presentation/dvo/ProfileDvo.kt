package kz.tinkoff.homework_2.presentation.dvo

import androidx.annotation.ColorRes
import kz.tinkoff.coreui.R

data class ProfileDvo(
    val fullName: String,
    val avatarUrl: String,
    val presence: PRESENCE = PRESENCE.OFFLINE,
) {

    enum class PRESENCE(val status: String, @ColorRes val colorResId: Int) {
        ACTIVE("active", R.color.green_2),
        IDLE("idle", R.color.orange),
        OFFLINE("offline", R.color.gray),
    }
}