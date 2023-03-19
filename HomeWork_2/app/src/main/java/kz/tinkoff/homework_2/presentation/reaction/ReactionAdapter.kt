package kz.tinkoff.homework_2.presentation.reaction

import android.content.Context
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.AbsListView
import android.widget.BaseAdapter
import android.widget.TextView

class ReactionAdapter(context: Context, emojiList: List<String>, private val listener: (String) -> Unit) :
    BaseAdapter() {
    private val mContext: Context
    private val mEmojiList: List<String>

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
        textView.setOnClickListener { listener(textView.text.toString()) }

        textView.text = mEmojiList[position]
        return textView
    }
}
