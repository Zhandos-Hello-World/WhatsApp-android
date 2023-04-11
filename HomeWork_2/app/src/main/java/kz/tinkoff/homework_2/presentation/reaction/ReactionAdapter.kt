package kz.tinkoff.homework_2.presentation.reaction

import android.content.Context
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.AbsListView
import android.widget.BaseAdapter
import android.widget.TextView
import kz.tinkoff.core.emoji.EmojiNCS

class ReactionAdapter(context: Context, emojiList: List<EmojiNCS>, private val listener: (EmojiNCS) -> Unit) :
    BaseAdapter() {
    private val mContext: Context
    private val mEmojiList: List<EmojiNCS>

    init {
        mContext = context
        mEmojiList = emojiList
    }

    override fun getCount(): Int {
        return mEmojiList.size
    }

    override fun getItem(position: Int): Any {
        return mEmojiList[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val textView: TextView
        if (convertView == null) {
            textView = TextView(mContext)
            textView.layoutParams = AbsListView.LayoutParams(150, 150)
            textView.gravity = Gravity.CENTER
            textView.textSize = 24f
        } else {
            textView = convertView as TextView
        }
        textView.setOnClickListener { listener(mEmojiList[position]) }

        textView.text = mEmojiList[position].getCodeString()
        return textView
    }
}
