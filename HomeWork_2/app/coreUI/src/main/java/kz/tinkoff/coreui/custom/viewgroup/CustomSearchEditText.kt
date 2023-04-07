package kz.tinkoff.coreui.custom.viewgroup

import android.content.Context
import android.os.Build
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import android.widget.ImageView
import androidx.annotation.RequiresApi
import com.google.android.material.textfield.TextInputEditText
import kz.tinkoff.coreui.databinding.CustomSearchEditTextBinding
import kz.tinkoff.coreui.ext.hideKeyboard
import kz.tinkoff.coreui.ext.showKeyboard

@RequiresApi(Build.VERSION_CODES.LOLLIPOP)
class CustomSearchEditText @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
    defStyleRes: Int = 0,
) : FrameLayout(context, attrs, defStyleAttr, defStyleRes) {

    private val viewBinding: CustomSearchEditTextBinding

    private val textInputEditText: TextInputEditText get() = viewBinding.textInputEditText
    private val searchEditText: TextInputEditText get() = viewBinding.textInputEditText
    private val searchBtn: ImageView get() = viewBinding.searchBtn

    init {
        viewBinding = CustomSearchEditTextBinding.inflate(LayoutInflater.from(context), this, true)

        searchBtn.setOnClickListener {
            textInputEditText.showKeyboard()
        }
    }


    fun setHint(text: CharSequence) {
        searchEditText.hint = text
    }

    fun doOnTextChanged(listener: (String) -> Unit) {
        searchEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                listener(s.toString())
            }

            override fun afterTextChanged(s: Editable?) {}
        })
    }

    fun setOnFocusChangeListener(focusListener: (Boolean) -> Unit) {
        textInputEditText.setOnFocusChangeListener { v, hasFocus ->
            focusListener(hasFocus)
        }
    }

    fun hideKeyboard() {
        textInputEditText.hideKeyboard()
    }

    fun setText(text: String) {
        textInputEditText.setText(text)
    }
}