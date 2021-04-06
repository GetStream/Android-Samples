package io.getstream.whatsappclone.ui.home

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.res.ResourcesCompat
import androidx.core.graphics.drawable.DrawableCompat
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import io.getstream.whatsappclone.R
import io.getstream.whatsappclone.ui.home.HomePagerAdapter.Companion.TAB_CAMERA
import io.getstream.whatsappclone.ui.home.HomePagerAdapter.Companion.TAB_CHATS
import io.getstream.whatsappclone.ui.home.HomePagerAdapter.Companion.TAB_TITLES
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class HomeFragment : Fragment(R.layout.fragment_home) {
    private lateinit var viewPager: ViewPager2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_main, menu)
        super.onCreateOptionsMenu(menu,inflater)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val activity : AppCompatActivity = activity as AppCompatActivity
        val fragmentView = requireNotNull(view) {"View should not be null when calling onActivityCreated"}

        val toolbar: Toolbar = fragmentView.findViewById(R.id.toolbar)
        activity.setSupportActionBar(toolbar)

        val tabLayout : TabLayout = fragmentView.findViewById(R.id.tabs)
        viewPager = fragmentView.findViewById(R.id.view_pager)
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
