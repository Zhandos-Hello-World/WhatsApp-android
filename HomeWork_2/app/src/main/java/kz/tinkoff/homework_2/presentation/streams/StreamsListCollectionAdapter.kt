package kz.tinkoff.homework_2.presentation.streams

import android.annotation.SuppressLint
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import kz.tinkoff.homework_2.presentation.streams.list.StreamsListFragment

class StreamsListCollectionAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {
    private val fragments: MutableList<Fragment> = mutableListOf()

    override fun getItemCount(): Int {
        return fragments.size
    }

    override fun createFragment(position: Int): Fragment {
        return fragments[position]
    }

    @SuppressLint("NotifyDataSetChanged")
    fun update(fragments: List<Fragment>) {
        this.fragments.clear()
        this.fragments.addAll(fragments)
        notifyDataSetChanged()
    }


    fun getAll(position: Int) {
        (fragments[position] as? StreamsListFragment)?.getAll()
    }

    companion object {
        const val SUBSCRIBED = 0
        const val ALL_STREAMS = 1

        private const val STREAM_TYPE = 2
    }
}
