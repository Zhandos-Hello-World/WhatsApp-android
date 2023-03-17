package kz.tinkoff.coreui.custom.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.os.Build
import android.util.AttributeSet
import android.view.View
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.core.content.withStyledAttributes
import kz.tinkoff.core.utils.dp
import kz.tinkoff.core.utils.sp
import kz.tinkoff.coreui.R

@RequiresApi(Build.VERSION_CODES.LOLLIPOP)
class DateText @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
    defStyleRes: Int = 0,
) : View(context, attrs, defStyleAttr, defStyleRes) {

    var dateText: String = "6 March"
        set(value) {
            field = value
            requestLayout()
        }

    init {
        context.withStyledAttributes(attrs, R.styleable.DateText) {
            val text = getString(R.styleable.DateText_dateText).orEmpty()
            if (text.isNotEmpty()) {
                dateText = text
            }
        }
    }

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