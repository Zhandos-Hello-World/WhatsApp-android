package kz.tinkoff.homework_2.presentation.create_stream

import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.core.view.MenuProvider
import kz.tinkoff.homework_2.R

class CreateStreamMenuProvider : MenuProvider {

    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        menuInflater.inflate(R.menu.create_stream_menu, menu)
    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        return when (menuItem.itemId) {
            R.id.done -> {
                true
            }
            else -> {
                false
            }
        }
    }

}
