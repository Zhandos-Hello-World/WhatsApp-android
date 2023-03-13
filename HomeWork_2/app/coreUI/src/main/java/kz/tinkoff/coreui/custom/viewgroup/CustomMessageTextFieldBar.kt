package kz.tinkoff.coreui.custom.viewgroup

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import kz.tinkoff.coreui.R

@RequiresApi(Build.VERSION_CODES.LOLLIPOP)
class CustomMessageTextFieldBar @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
    defStyleRes: Int = 0,
) : FrameLayout(context, attrs, defStyleAttr, defStyleRes) {

    private var state = SendMessageState.SEND_MESSAGE
    private var textChangedListener: ((CharSequence) -> Unit)? = null

    private val textInputLayout: TextInputLayout by lazy { findViewById(R.id.textInputLayout) }
    private val messageEditText: TextInputEditText by lazy { findViewById(R.id.message_edit_text) }
    private val sendMessageBtn: FloatingActionButton by lazy { findViewById(R.id.send_message_btn) }

    private var text: String = ""

    init {
        View.inflate(
            context,
            R.layout.custom_message_text_field_bar,
            this
        )
        setState(state)
        messageEditText.clearFocus()
        configureMessageEditText()
    }


    @SuppressLint("ClickableViewAccessibility")
    private fun configureMessageEditText() {
        messageEditText.setOnTouchListener { _, _ ->
            messageEditText.isFocusable = true
            messageEditText.isFocusableInTouchMode = true
            messageEditText.hint = ""
            textInputLayout.hint = ""
            false
        }


        messageEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                state = if (s.toString().isEmpty()) SendMessageState.SEND_OTHER else SendMessageState.SEND_MESSAGE
                setState(state)
                textChangedListener?.invoke(s.toString())
                text = s.toString()
            }

            override fun afterTextChanged(s: Editable?) {}
        })
    }


    private fun setState(state: SendMessageState) {
        when (state) {
            SendMessageState.SEND_MESSAGE -> {
                configureToSendMessage()
            }
            SendMessageState.SEND_OTHER -> {
                configureToSendOthers()
            }
        }
    }

    private fun configureToSendMessage() {
        sendMessageBtn.setImageDrawable(
            ContextCompat.getDrawable(
                context,
                R.drawable.baseline_send_24
            )
        )
        sendMessageBtn.setBackgroundColor(ContextCompat.getColor(context, R.color.green_2))
    }

    private fun configureToSendOthers() {
        sendMessageBtn.setImageDrawable(
            ContextCompat.getDrawable(
                context,
                R.drawable.baseline_add_24
            )
        )
        sendMessageBtn.setBackgroundColor(ContextCompat.getColor(context, R.color.black_gray_4))
    }

    fun setOnSendClickListener(listener: (state: SendMessageState) -> Unit) {
        sendMessageBtn.setOnClickListener {
            listener(state)
        }
    }

    fun addWatchTextChanged(textChangedListener: (CharSequence) -> Unit) {
        this.textChangedListener = textChangedListener
    }

    fun setHint(text: CharSequence) {
        textInputLayout.hint = text
    }

    fun getText(): CharSequence {
        return text
    }

    fun clearText() {
        messageEditText.setText("")
    }

    enum class SendMessageState {
        SEND_MESSAGE, SEND_OTHER
    }
}