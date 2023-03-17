package kz.tinkoff.coreui.custom.viewgroup

import android.content.Context
import android.os.Build
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.cardview.widget.CardView
import kz.tinkoff.core.utils.getMaxWidth
import kz.tinkoff.coreui.custom.dvo.MessageDvo
import kz.tinkoff.coreui.databinding.CustomMessageViewGroupContentBinding
import kz.tinkoff.coreui.item.ReactionViewItem

@RequiresApi(Build.VERSION_CODES.LOLLIPOP)
class MessageViewGroup @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
    defStyleRes: Int = 0,
) : ViewGroup(context, attrs, defStyleAttr, defStyleRes) {


    private lateinit var viewBinding: CustomMessageViewGroupContentBinding

    private val reactionsViewGroup: ReactionViewGroup by lazy { viewBinding.reactions }
    private val avatarView: ImageView by lazy { viewBinding.avatar }
    private val messageCardView: CardView by lazy { viewBinding.messageCard }
    private val usernameView: TextView by lazy { viewBinding.title }
    private val messageView: TextView by lazy { viewBinding.messageText }

    private var data: MessageDvo? = null


    init {
        viewBinding = CustomMessageViewGroupContentBinding.inflate(
            LayoutInflater.from(context),
            this
        )
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        measureChildren(widthMeasureSpec, heightMeasureSpec)

        val totalWidth = paddingLeft + paddingRight + avatarView.measuredWidth + messageCardView.measuredWidth
        val totalHeight =
            paddingTop + paddingBottom + maxOf(
                avatarView.measuredHeight,
                messageCardView.measuredHeight
            ) + reactionsViewGroup.measuredHeight
        setMeasuredDimension(totalWidth, totalHeight)
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        var offsetX = paddingStart
        var offsetY = paddingTop

        avatarView.layout(
            offsetX,
            offsetY,
            offsetX + avatarView.measuredWidth,
            offsetY + avatarView.measuredHeight
        )

        offsetX += avatarView.measuredWidth


        messageCardView.layout(
            offsetX + PADDING_BETWEEN_ITEMS,
            offsetY,
            offsetX + messageCardView.measuredWidth - PADDING_BETWEEN_ITEMS,
            offsetY + messageCardView.measuredHeight
        )

        offsetY += messageCardView.measuredHeight

        reactionsViewGroup.layout(
            offsetX + PADDING_BETWEEN_ITEMS,
            offsetY,
            getMaxWidth(),
            offsetY + reactionsViewGroup.measuredHeight
        )
    }

    fun setMessageDvo(dvo: MessageDvo) {
        this.data = dvo
        usernameView.text = dvo.title
        messageView.text = dvo.message
        reactionsViewGroup.submitReactions(dvo.reactions)
    }

    fun addReactionClickListener(listener: (MessageDvo) -> Unit) {
        messageCardView.setOnLongClickListener {
            data?.let {
                listener(it)
            }
            true
        }
        reactionsViewGroup.addReactionClickListener {
            data?.let {
                listener(it)
            }
        }
    }

    fun setEmojiClickListener(listener: (MessageDvo, ReactionViewItem) -> Unit) {
        reactionsViewGroup.setEmojiClickListener { reactionViewItem ->
            data?.let {
                listener(it, reactionViewItem)
            }
        }

    }

    companion object {
        private const val PADDING_BETWEEN_ITEMS = 9
    }

}