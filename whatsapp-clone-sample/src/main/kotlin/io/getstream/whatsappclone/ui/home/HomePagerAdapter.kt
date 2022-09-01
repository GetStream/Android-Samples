/*
 * Copyright 2022 Stream.IO, Inc. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.getstream.whatsappclone.ui.home

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import io.getstream.whatsappclone.ui.channels.ChannelListFragment

class HomePagerAdapter(
    fragmentManager: FragmentManager,
    lifecycle: Lifecycle,
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
