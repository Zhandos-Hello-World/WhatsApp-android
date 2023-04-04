package kz.tinkoff.coreui.custom.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.util.AttributeSet
import android.view.View
import kz.tinkoff.core.utils.getMaxWidth
import kz.tinkoff.core.utils.sp
import kz.tinkoff.core.R
import kz.tinkoff.coreui.R as uiR


class CustomSubToolbar @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
    defStyleRes: Int = 0,
) : View(context, attrs, defStyleAttr, defStyleRes) {

    private val topicText: String = context.getString(R.string.topic)
    private var descText: String = context.getString(R.string.test)
    private val title: String get() = topicText + descText

    private val textPaint = Paint().apply {
        textSize = 20F.sp(context)
        color = context.getColor(uiR.color.white_gray)
    }
    private val textBounds = Rect()

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        textPaint.getTextBounds(title, 0, title.length, textBounds)

        val maxWidth = getMaxWidth()
        val height = textBounds.height() + paddingTop + paddingBottom
        val measureHeight = resolveSize(height, heightMeasureSpec)

        setMeasuredDimension(maxWidth, measureHeight)
    }

    override fun onDraw(canvas: Canvas?) {
        val startY = textBounds.height() / 2 - textBounds.exactCenterY() + paddingBottom
        val startX = (getMaxWidth() - textBounds.width()) / 2F
        canvas?.drawText(title, startX, startY, textPaint)
    }

    fun setText(text: String) {
        descText = text
        requestLayout()
    }
}