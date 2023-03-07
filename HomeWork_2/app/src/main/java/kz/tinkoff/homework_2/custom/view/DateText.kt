package kz.tinkoff.homework_2.custom.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.util.AttributeSet
import android.view.View
import androidx.core.content.ContextCompat
import kz.tinkoff.homework_2.R
import kz.tinkoff.homework_2.utils.dp
import kz.tinkoff.homework_2.utils.sp

class DateText @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
    defStyleRes: Int = 0,
) : View(context, attrs, defStyleAttr, defStyleRes) {

    private var dateText: String = "6 March"

    private val dateTextPaint = Paint().apply {
        textSize = 14F.sp(context)
        color = ContextCompat.getColor(context, R.color.gray)
    }
    private val dateTextBounds = Rect()

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        dateTextPaint.getTextBounds(dateText, 0, dateText.length, dateTextBounds)

        val width =
            dateTextBounds.width() + (2 * DATE_DEFAULT_HORIZONTAL_PADDING.dp(context)).toInt()
        val height =
            dateTextBounds.height() + (6 * DATE_DEFAULT_VERTICAL_PADDING.dp(context)).toInt()

        val measureWidth = resolveSize(width, widthMeasureSpec)
        val measureHeight = resolveSize(height, heightMeasureSpec)

        setMeasuredDimension(measureWidth, measureHeight)
    }

    override fun onDraw(canvas: Canvas?) {
        val startY =
            dateTextBounds.height() / 2 - dateTextBounds.exactCenterY() + paddingBottom +
                    (2 * DATE_DEFAULT_VERTICAL_PADDING.dp(
                        context
                    ))

        canvas?.drawText(
            dateText,
            DATE_DEFAULT_HORIZONTAL_PADDING.dp(context),
            startY,
            dateTextPaint
        )
    }

    companion object {
        private const val DATE_DEFAULT_HORIZONTAL_PADDING = 20F
        private const val DATE_DEFAULT_VERTICAL_PADDING = 2F
    }
}