package kz.tinkoff.homework_2.utils

import android.view.View

fun View.getMaxWidth(): Int {
    return context.resources.displayMetrics.widthPixels
}

fun View.getMaxHeight(): Int {
    return context.resources.displayMetrics.heightPixels
}