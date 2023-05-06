package kz.tinkoff.coreui.custom.viewgroup

import android.content.Context
import android.os.Build
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import java.lang.Integer.max
import kotlin.math.ceil
import kz.tinkoff.coreui.R
import kz.tinkoff.coreui.custom.view.AddReactionView
import kz.tinkoff.coreui.custom.view.ReactionView
import kz.tinkoff.coreui.item.ReactionViewItem

@RequiresApi(Build.VERSION_CODES.LOLLIPOP)
class ReactionViewGroup @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
    defStyleRes: Int = 0,
) : ViewGroup(context, attrs, defStyleAttr, defStyleRes) {
    private var reactionViews = mutableListOf<ReactionView>()
    private var addReaction: AddReactionView? = null

    private var listener: ((ReactionViewItem) -> Unit)? = null

    private var mNumColumns = 2
    private var childMeasuredWidth = -1
    private var childMeasuredHeight = -1

    public override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        for (i in 0 until childCount) {
            val child = getChildAt(i)

            child.measure(
                MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED),
                MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED)
            )
            if (childMeasuredWidth == -1) {
                childMeasuredWidth = child.measuredWidth
                childMeasuredHeight = child.measuredHeight
            }
        }

        val widthSize = MeasureSpec.getSize(widthMeasureSpec)

        mNumColumns = widthSize / (childMeasuredWidth + HORIZONTAL_PADDING)

        val rowCount = ceil(childCount.toFloat() / mNumColumns).toInt()
        val height = rowCount * (childMeasuredHeight + VERTICAL_PADDING)
        setMeasuredDimension(widthSize, height)
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        val childCount = childCount

        for (i in 0 until childCount) {
            val child = getChildAt(i)
            drawChild(i, child)
        }
    }

    private fun drawChild(childIndex: Int, child: View) {
        val row = childIndex / mNumColumns
        val column = childIndex % mNumColumns

        val left = column * child.measuredWidth
        val top = row * child.measuredHeight
        val right = left + child.measuredWidth
        val bottom = top + child.measuredHeight

        child.layout(left + HORIZONTAL_PADDING, top + VERTICAL_PADDING, right, bottom)
    }

    fun submitReaction(viewItem: ReactionViewItem) {
        val reactionView = ReactionView(context)
        reactionView.setReactionType(viewItem)

        reactionViews.add(reactionView)
        addView(reactionView)
    }

    fun submitReactions(viewItems: List<ReactionViewItem>) {
        removeAllViews()
        with(viewItems) {
            forEach {
                submitReaction(it)
            }

            reactionViews.forEach { reactionView ->
                reactionView.setOnClickListener {
                    listener?.invoke(reactionView.getItem())
                }
            }
        }
        requestLayout()
    }

    fun addReactionClickListener(listener: () -> Unit) {
        addReaction?.setOnClickListener {
            listener.invoke()
        }
    }

    fun setEmojiClickListener(listener: (ReactionViewItem) -> Unit) {
        this.listener = listener
    }

    companion object {
        const val HORIZONTAL_PADDING = 2
        const val VERTICAL_PADDING = 2
    }

}
