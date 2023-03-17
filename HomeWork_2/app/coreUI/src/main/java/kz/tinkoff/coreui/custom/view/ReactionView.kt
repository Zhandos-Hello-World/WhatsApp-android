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
import androidx.core.content.withStyledAttributes
import kz.tinkoff.core.utils.sp
import kz.tinkoff.coreui.R
import kz.tinkoff.coreui.item.ReactionViewItem

@RequiresApi(Build.VERSION_CODES.LOLLIPOP)
class ReactionView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
    defStyleRes: Int = 0,
) : View(context, attrs, defStyleAttr, defStyleRes) {
    private var item = ReactionViewItem.defaultReaction()
    private val emojiWithCount get() = "${item.emoji}${item.count}"

    private val textPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        textSize = 24F.sp(context)
        color = Color.WHITE
    }
    private val textBounds = Rect()

    init {
        context.withStyledAttributes(attrs, R.styleable.ReactionView) {
            item = ReactionViewItem(
                id = 0,
                count = getInt(R.styleable.ReactionView_count, 0),
                emoji = getString(R.styleable.ReactionView_emoji).orEmpty(),
            )
        }
        setBackgroundResource(R.drawable.bg_emoji_view)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        textPaint.getTextBounds(emojiWithCount, 0, emojiWithCount.length, textBounds)

        val textWidth = textBounds.width() + paddingStart + paddingEnd
        val textHeight = textBounds.height() + paddingTop + paddingBottom

        val measureWidth = resolveSize(textWidth, widthMeasureSpec)
        val measureHeight = resolveSize(textHeight, heightMeasureSpec)

        setMeasuredDimension(measureWidth, measureHeight)
    }

    override fun onDraw(canvas: Canvas?) {
        val startY = textBounds.height() / 2 - textBounds.exactCenterY() + paddingBottom
        canvas?.drawText(emojiWithCount, paddingTop.toFloat(), startY, textPaint)
    }

    fun setReactionType(item: ReactionViewItem) {
        this.item = item
        requestLayout()
    }


    fun getItem(): ReactionViewItem {
        return item
    }
}