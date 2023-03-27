package kz.tinkoff.coreui.ext

import android.view.View
import androidx.core.view.isVisible

fun View.show(visibility: Boolean = true) {
    this.isVisible = visibility
}