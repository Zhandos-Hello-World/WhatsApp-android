package kz.tinkoff.coreui.custom.viewgroup

import android.content.Context
import android.os.Build
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import kz.tinkoff.core.utils.getMaxWidth
import kz.tinkoff.core.utils.sp
import kz.tinkoff.coreui.R

@RequiresApi(Build.VERSION_CODES.LOLLIPOP)
class CustomToolbar @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
    defStyleRes: Int = 0,
) : ViewGroup(context, attrs, defStyleAttr, defStyleRes) {

    private val image: View
    private val text: View
    private val indentBigger = context.resources.getDimension(R.dimen.indent_bigger).toInt()
    private val indentBase = context.resources.getDimension(R.dimen.indent_base).toInt()


    init {
        inflate(context, R.layout.custom_toolbar, this)

        image = findViewById(R.id.back)
        text = findViewById(R.id.text)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        measureChildren(widthMeasureSpec, heightMeasureSpec)

        val maxWidth = getMaxWidth()
        val totalHeight =
            paddingTop + paddingBottom + maxOf(image.measuredHeight, text.measuredHeight) + indentBigger + indentBigger
        setMeasuredDimension(maxWidth, totalHeight)
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        var offsetX = paddingStart + indentBigger
        val offsetY = paddingTop + indentBigger

        image.layout(
            offsetX,
            offsetY - indentBase,
            offsetX + image.measuredWidth,
            offsetY + image.measuredHeight + indentBigger
        )

        offsetX += image.measuredWidth + 44F.sp(context).toInt()

        text.layout(
            offsetX,
            offsetY,
            offsetX + text.measuredWidth,
            offsetY + text.measuredHeight + indentBigger
        )
    }

    fun setOnBackClickListener(listener: () -> Unit) {
        image.setOnClickListener {listener.invoke() }
    }
}