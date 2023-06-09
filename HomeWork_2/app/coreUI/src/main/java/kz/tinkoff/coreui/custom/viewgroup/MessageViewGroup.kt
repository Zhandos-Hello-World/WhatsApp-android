package kz.tinkoff.coreui.custom.viewgroup

import android.content.Context
import android.os.Build
import android.text.Html
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.cardview.widget.CardView
import com.bumptech.glide.Glide
import com.google.android.material.imageview.ShapeableImageView
import kz.tinkoff.coreui.R
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
    private val avatarView: ShapeableImageView by lazy { viewBinding.avatar }
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

        val widthSize = MeasureSpec.getSize(widthMeasureSpec)

        val totalHeight =
            paddingTop + paddingBottom + maxOf(
                avatarView.measuredHeight,
                messageCardView.measuredHeight
            ) + reactionsViewGroup.measuredHeight
        setMeasuredDimension(widthSize, totalHeight)
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
            width,
            offsetY + messageCardView.measuredHeight
        )

        offsetY += messageCardView.measuredHeight

        reactionsViewGroup.layout(
            offsetX + PADDING_BETWEEN_ITEMS,
            offsetY,
            width,
            offsetY + reactionsViewGroup.measuredHeight
        )
    }

    fun setMessageDvo(dvo: MessageDvo) {
        this.data = dvo
        usernameView.text = dvo.senderFullName
        messageView.text = parseHtmlValue(dvo.content)
        reactionsViewGroup.submitReactions(dvo.reactions)

        Glide.with(this)
            .load(dvo.avatarUrl)
            .placeholder(R.drawable.placeholder)
            .error(R.drawable.ic_placeholder_error_state)
            .into(avatarView)
    }

    private fun parseHtmlValue(html: String): String {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            Html.fromHtml(html, Html.FROM_HTML_MODE_LEGACY)
        } else {
            Html.fromHtml(html)
        }.toString().trim()
    }

    fun setLongClickListener(listener: (MessageDvo) -> Unit) {
        messageCardView.setOnLongClickListener {
            data?.let(listener)
            true
        }
    }

    fun setOnReactionClickListener(listener: (ReactionViewItem) -> Unit) {
        reactionsViewGroup.setEmojiClickListener { reactionViewItem ->
            listener.invoke(reactionViewItem)
        }
    }

    companion object {
        private const val PADDING_BETWEEN_ITEMS = 9
    }

}
