package io.getstream.whatsappclone.ui.home

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import io.getstream.whatsappclone.ui.channel_list.ChannelListFragment

class HomePagerAdapter(
    fragmentManager: FragmentManager,
    lifecycle: Lifecycle
) : FragmentStateAdapter(fragmentManager, lifecycle) {

    override fun getItemCount(): Int = PAGE_COUNT

    override fun createFragment(position: Int): Fragment {
        return if (position == TAB_CHATS) {
            ChannelListFragment()
        } else {
            HomePageFragment.newInstance(TAB_TITLES[position] ?: "")
        }
    }

    companion object {
        const val TAB_CAMERA = 0
        const val TAB_CHATS = 1
        const val TAB_STATUS = 2
        const val TAB_CALLS = 3
        private const val PAGE_COUNT = 4

        val TAB_TITLES = mapOf(
            TAB_CAMERA to "camera",
            TAB_CHATS to "chats",
            TAB_STATUS to "status",
            TAB_CALLS to "calls"
        )
    }
}
