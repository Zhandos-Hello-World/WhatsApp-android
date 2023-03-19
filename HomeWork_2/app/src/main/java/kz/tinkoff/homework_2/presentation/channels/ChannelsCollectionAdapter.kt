package kz.tinkoff.homework_2.presentation.channels

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import kz.tinkoff.homework_2.presentation.channels.list.ChannelsListFragment

class ChannelsCollectionAdapter(private val fragment: Fragment) : FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int {
        return STREAM_TYPE
    }

    override fun createFragment(position: Int): Fragment {
       return  when (position) {
            SUBSCRIBED -> ChannelsListFragment.getINSTANCE()
            else -> ChannelsListFragment.getINSTANCE()
        }
    }

    companion object {
        const val SUBSCRIBED = 0
        const val ALL_STREAMS = 1

        private const val STREAM_TYPE = 2
    }
}