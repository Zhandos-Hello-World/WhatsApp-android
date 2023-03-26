package kz.tinkoff.coreui.custom.viewgroup

import android.content.Context
import android.os.Build
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView
import androidx.annotation.RequiresApi
import kz.tinkoff.coreui.R

@RequiresApi(Build.VERSION_CODES.LOLLIPOP)
class CustomAvatar @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
    defStyleRes: Int = 0,
) : FrameLayout(context, attrs, defStyleAttr, defStyleRes) {
    private val onlineView: View by lazy { findViewById(R.id.online_view) }
    private val image: ImageView by lazy { findViewById(R.id.avatar) }

    var isOnline: Boolean = false
        set(value) {
            field = value
            onlineView.visibility = if (field) VISIBLE else GONE
        }

    init {
        View.inflate(
            context,
            R.layout.custom_avatar,
            this
        )
        onlineView.visibility = if (isOnline) VISIBLE else GONE
    }

}