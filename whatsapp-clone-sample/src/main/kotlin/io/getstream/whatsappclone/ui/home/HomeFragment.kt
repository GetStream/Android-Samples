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

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import androidx.core.graphics.drawable.DrawableCompat
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayoutMediator
import io.getstream.whatsappclone.R
import io.getstream.whatsappclone.databinding.FragmentHomeBinding
import io.getstream.whatsappclone.ui.home.HomePagerAdapter.Companion.TAB_CAMERA
import io.getstream.whatsappclone.ui.home.HomePagerAdapter.Companion.TAB_CHATS
import io.getstream.whatsappclone.ui.home.HomePagerAdapter.Companion.TAB_TITLES

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_main, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            val activity: AppCompatActivity = activity as AppCompatActivity
            activity.setSupportActionBar(toolbar)

            viewPager.adapter = HomePagerAdapter(childFragmentManager, lifecycle)

            // connect the tabs and view pager2
            TabLayoutMediator(tabLayout, viewPager) { tab, position ->
                if (position == TAB_CAMERA) {
                    tab.setIcon(R.drawable.ic_camera_alt_black_24dp)
                    val colors = ResourcesCompat.getColorStateList(resources, R.color.tab_icon, activity.theme)
                    tab.icon?.apply { DrawableCompat.setTintList(DrawableCompat.wrap(this), colors) }
                } else {
                    tab.text = TAB_TITLES[position]
                }
                viewPager.setCurrentItem(tab.position, true)
            }.attach()
            tabLayout.getTabAt(TAB_CHATS)?.let { tabLayout.selectTab(it) }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
