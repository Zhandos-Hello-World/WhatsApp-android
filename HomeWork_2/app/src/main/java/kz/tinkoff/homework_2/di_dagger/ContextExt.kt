package kz.tinkoff.homework_2.di_dagger

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import kz.tinkoff.homework_2.MainApplication

fun Fragment.getApplication(): MainApplication {
    return (requireActivity().application as MainApplication)
}

fun AppCompatActivity.getMainApplication(): MainApplication {
    return this.application as MainApplication
}
