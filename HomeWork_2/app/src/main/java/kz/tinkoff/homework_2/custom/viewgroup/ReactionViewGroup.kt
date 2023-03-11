package kz.tinkoff.homework_2.custom.viewgroup

import android.content.Context
import android.util.AttributeSet
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.core.content.ContextCompat
import java.lang.Integer.max
import kz.tinkoff.homework_2.R
import kz.tinkoff.homework_2.custom.item.ReactionViewItem
import kz.tinkoff.homework_2.custom.view.ReactionView

class ReactionViewGroup @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
    defStyleRes: Int = 0,
) : ViewGroup(context, attrs, defStyleAttr, defStyleRes) {
    private var numColumns = 8
    private var horizontalSpacing = 10
    private var verticalSpacing = 7
    private var reactions = mutableListOf<ReactionView>()

    init {
        attrs?.let {
            val typedArray = context.obtainStyledAttributes(attrs, R.styleable.ReactionViewGroup)
            numColumns = typedArray.getInteger(R.styleable.ReactionViewGroup_numColumns, 2)
            horizontalSpacing =
                typedArray.getDimensionPixelSize(R.styleable.ReactionViewGroup_horizontalSpacing, 0)
            verticalSpacing =
                typedArray.getDimensionPixelSize(R.styleable.ReactionViewGroup_verticalSpacing, 0)
            typedArray.recycle()
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val parentWidth = MeasureSpec.getSize(widthMeasureSpec)
        val columnWidth = (parentWidth - horizontalSpacing * (numColumns - 1)) / numColumns
        var xPosition = 0
        var yPosition = 0
        var maxHeightInRow = 0

        for (i in 0 until childCount) {
            val child = getChildAt(i)

            val childWidthMeasureSpec =
                MeasureSpec.makeMeasureSpec(columnWidth, MeasureSpec.EXACTLY)
            val childHeightMeasureSpec = MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED)

            child.measure(childWidthMeasureSpec, childHeightMeasureSpec)

            val childWidth = child.measuredWidth
            val childHeight = child.measuredHeight

            if (xPosition + childWidth > parentWidth) {
                xPosition = 0
                yPosition += maxHeightInRow + verticalSpacing
                maxHeightInRow = 0
            }

            val left = xPosition
            val top = yPosition
            val right = left + childWidth
            val bottom = top + childHeight

            xPosition += childWidth + horizontalSpacing
            maxHeightInRow = max(maxHeightInRow, childHeight)

            child.layout(left, top, right, bottom)
        }

        val totalHeight = yPosition + maxHeightInRow
        setMeasuredDimension(parentWidth, totalHeight)
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
    }

    fun addReaction(viewItem: ReactionViewItem) {
        val reactionView = ReactionView(context)
        reactionView.setReactionType(viewItem)

        addView(reactionView)
        reactions.add(reactionView)
    }

    fun addReactions(viewItems: List<ReactionViewItem>) {
        viewItems.forEach {
            addReaction(it)
        }
        addView(ImageButton(context).apply {
            setImageDrawable(ContextCompat.getDrawable(context, R.drawable.baseline_add_24))
            setBackgroundColor(ContextCompat.getColor(context, R.color.black_gray))
        })
    }

    fun setEmojiClickListener(listener: (ReactionView) -> Unit){
        reactions.forEach { reactionView ->
            reactionView.setEmojiClickListener(listener)
        }
    }
}