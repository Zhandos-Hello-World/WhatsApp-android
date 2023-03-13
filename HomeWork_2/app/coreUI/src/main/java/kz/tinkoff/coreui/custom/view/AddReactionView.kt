package kz.tinkoff.coreui.custom.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.os.Build
import android.util.AttributeSet
import android.view.View
import androidx.annotation.RequiresApi
import kz.tinkoff.core.utils.sp
import kz.tinkoff.coreui.R

@RequiresApi(Build.VERSION_CODES.LOLLIPOP)
class AddReactionView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
    defStyleRes: Int = 0,
) : View(context, attrs, defStyleAttr, defStyleRes) {
    private val symbol get() = PLUS

    private val textPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        textSize = 24F.sp(context)
        color = Color.WHITE
    }
    private val textBounds = Rect()

    init {
        setBackgroundResource(R.drawable.bg_emoji_view)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        textPaint.getTextBounds(symbol, 0, symbol.length, textBounds)

        val textWidth = textBounds.width() + paddingStart + paddingEnd
        val textHeight = textBounds.height() + paddingTop + paddingBottom

        val measureWidth = resolveSize(textWidth, widthMeasureSpec)
        val measureHeight = resolveSize(textHeight, heightMeasureSpec)

        setMeasuredDimension(measureWidth, measureHeight)
    }

    override fun onDraw(canvas: Canvas?) {
        val startY = textBounds.height() / 2 - textBounds.exactCenterY() + paddingBottom
        val centerX = paddingTop.toFloat() + measuredWidth / 2 - textBounds.width()

        canvas?.drawText(symbol, centerX, startY, textPaint)
    }

    fun setAddReactionClickListener(listener: () -> Unit) {
        this.setOnClickListener { listener.invoke() }
    }

    companion object {
        private const val PLUS = "+"
    }
}