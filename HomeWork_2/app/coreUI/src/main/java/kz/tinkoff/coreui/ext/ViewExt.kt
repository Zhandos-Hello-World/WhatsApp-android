package kz.tinkoff.coreui.ext

import android.view.View
import androidx.core.view.isVisible

fun View.show() {
    this.isVisible = true
}

fun View.hide() {
    this.isVisible = false
}