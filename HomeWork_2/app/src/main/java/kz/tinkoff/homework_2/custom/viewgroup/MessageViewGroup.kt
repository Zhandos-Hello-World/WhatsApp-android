package kz.tinkoff.homework_2.custom.viewgroup

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import androidx.core.view.forEach
import kz.tinkoff.homework_2.R
import kz.tinkoff.homework_2.custom.item.ReactionViewItem
import kz.tinkoff.homework_2.custom.view.ReactionView
import kz.tinkoff.homework_2.utils.getMaxWidth

class MessageViewGroup @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
    defStyleRes: Int = 0,
) : ViewGroup(context, attrs, defStyleAttr, defStyleRes) {

    private val image: View
    private val card: View
    private val reactions: ReactionViewGroup

    init {
        inflate(context, R.layout.custom_message_view_group_content, this)

        image = findViewById(R.id.image)
        card = findViewById(R.id.text)
        reactions = findViewById(R.id.reactions)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        measureChildren(widthMeasureSpec, heightMeasureSpec)

        val totalWidth = paddingLeft + paddingRight + image.measuredWidth + card.measuredWidth
        val totalHeight =
            paddingTop + paddingBottom + maxOf(image.measuredHeight, card.measuredHeight) + reactions.measuredHeight
        setMeasuredDimension(totalWidth, totalHeight)
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        var offsetX = paddingStart
        var offsetY = paddingTop

        image.layout(
            offsetX,
            offsetY,
            offsetX + image.measuredWidth,
            offsetY + image.measuredHeight
        )

        offsetX += image.measuredWidth


        card.layout(
            offsetX + PADDING_BETWEEN_ITEMS,
            offsetY,
            offsetX + card.measuredWidth - PADDING_BETWEEN_ITEMS,
            offsetY + card.measuredHeight
        )

        offsetY += card.measuredHeight

        reactions.layout(
            offsetX + PADDING_BETWEEN_ITEMS,
            offsetY,
            getMaxWidth(),
            offsetY + reactions.measuredHeight
        )
    }

    fun addReaction() {
        val list = listOf(
            ReactionViewItem(0, 3, "😹"),
            ReactionViewItem(0, 3, "💩"),
            ReactionViewItem(0, 3, "👻"),
        )
        reactions.addReactions(list)
        requestLayout()
    }

    companion object {
        private const val PADDING_BETWEEN_ITEMS = 9
    }

    fun setEmojiClickListener(listener: (ReactionView) -> Unit){
        reactions.setEmojiClickListener(listener)
    }
}