package kz.tinkoff.coreui.ext

import android.app.Activity
import android.os.Build
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.annotation.RequiresApi
import com.google.android.material.textfield.TextInputEditText

@RequiresApi(Build.VERSION_CODES.CUPCAKE)
fun TextInputEditText.showKeyboard() {
    this.post {
        this.requestFocus()
        val inputMethodManager: InputMethodManager =
            this.context.getSystemService(android.app.Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.showSoftInput(
            this,
            InputMethodManager.SHOW_IMPLICIT
        )
    }
}

@RequiresApi(Build.VERSION_CODES.CUPCAKE)
fun EditText.hideKeyboard() {
    val inputMethodManager: InputMethodManager =
        this.context.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.hideSoftInputFromWindow(this.windowToken, 0)
}