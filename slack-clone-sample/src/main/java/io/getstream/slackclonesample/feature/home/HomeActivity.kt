package io.getstream.slackclonesample.feature.home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.FrameLayout
import androidx.activity.viewModels
import androidx.core.os.bundleOf
import androidx.core.view.GravityCompat
import androidx.core.view.doOnLayout
import androidx.core.view.updatePadding
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import androidx.lifecycle.lifecycleScope
import io.getstream.slackclonesample.AppConfig
import io.getstream.slackclonesample.SlackSampleApp
import io.getstream.slackclonesample.feature.chat.ChatFragment
import io.getstream.slackclonesample.util.viewModelProviderFactoryOf
import io.getstream.chat.android.client.ChatClient
import io.getstream.chat.android.client.logger.ChatLogger
import io.getstream.chat.android.client.models.User
import io.getstream.chat.android.client.models.image
import io.getstream.chat.android.client.models.name
import io.getstream.chat.android.ui.channel.list.header.ChannelListHeaderView
import io.getstream.chat.android.ui.channel.list.header.viewmodel.ChannelListHeaderViewModel
import io.getstream.chat.android.ui.channel.list.header.viewmodel.bindView
import io.getstream.chat.android.ui.channel.list.viewmodel.ChannelListViewModel
import io.getstream.chat.android.ui.channel.list.viewmodel.factory.ChannelListViewModelFactory
import io.getstream.slackclonesample.R
import io.getstream.slackclonesample.databinding.ActivityMainBinding
import io.getstream.slackclonesample.domain.user.SampleUser
import io.getstream.slackclonesample.feature.home.channel.bindListView
import kotlinx.coroutines.flow.collect

class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val channelListViewModel: ChannelListViewModel by viewModels { ChannelListViewModelFactory() }
    private val homeViewModel: HomeViewModel by viewModels {
        viewModelProviderFactoryOf { HomeViewModelFactory().create() }
    }

    private val logger = ChatLogger.get("HomeActivity")

    private var isFirstTimeSetupDone: Boolean = false

    private val TAG = "HomeActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        init()
        setupViews()

        lifecycleScope.launchWhenStarted {
            homeViewModel.state.collect { renderState(it) }
        }
    }

    override fun onBackPressed() {
        if (binding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            binding.drawerLayout.closeDrawer(GravityCompat.START, true)
        }
        super.onBackPressed()
    }

    private fun renderState(state: HomeViewModel.State) {
        if (state.isDrawerOpen && !binding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            binding.drawerLayout.openDrawer(GravityCompat.START, true)
        } else if (!state.isDrawerOpen && binding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            binding.drawerLayout.closeDrawer(GravityCompat.START, true)
        }

        state.cid?.let {
            supportFragmentManager.commit {
                val bundle = bundleOf("cid" to it)
                replace<ChatFragment>(R.id.chatContainer, args = bundle)
            }
        }
    }

    private fun setupViews() {
        binding.navigationView.setOnApplyWindowInsetsListener { view, insets ->
            view.updatePadding(top = insets.systemWindowInsetTop)
            insets
        }
        // Instantiate the ViewModel
        val channelListHeaderViewModel: ChannelListHeaderViewModel by viewModels()

        val headerView = binding.navigationView.getHeaderView(0) as ChannelListHeaderView
        // Bind the ViewModel with ChannelListView
        channelListHeaderViewModel.bindView(
            headerView,
            this
        )
        headerView.doOnLayout {
            val layoutParams: FrameLayout.LayoutParams =
                binding.channelsView.layoutParams as FrameLayout.LayoutParams
            layoutParams.topMargin = headerView.height
        }

        binding.channelsView.setChannelClickListener {
            homeViewModel.submitAction(HomeViewModel.Action.SelectChannelAction(it.cid))
        }

        channelListViewModel.bindListView(binding.channelsView, this)

        // Handles correct drawer state
        binding.drawerLayout.addDrawerListener(object : DrawerLayout.SimpleDrawerListener() {
            override fun onDrawerClosed(drawerView: View) {
                super.onDrawerClosed(drawerView)
                homeViewModel.submitAction(HomeViewModel.Action.CloseDrawerAction)
            }

            override fun onDrawerOpened(drawerView: View) {
                super.onDrawerOpened(drawerView)
                homeViewModel.submitAction(HomeViewModel.Action.OpenDrawerAction)
            }
        })
    }

    private fun init() {
        val user = SlackSampleApp.instance.userRepository.getUser()
        if (user != SampleUser.None) {
            authenticateUser(user)
        } else {
            authenticateUser(AppConfig.availableUsers[0])
        }
    }

    private fun authenticateUser(user: SampleUser) {
        SlackSampleApp.instance.userRepository.setUser(user)
        val chatUser = User().apply {
            id = user.id
            image = user.image
            name = user.name
        }

        ChatClient.instance().connectUser(chatUser, user.token)
            .enqueue { result ->
                if (result.isSuccess) {
                    homeViewModel.submitAction(HomeViewModel.Action.UserConnectedAction(result.data().user.id))
                    observeChannels()
                } else {
                    //_events.postValue(Event(UiEvent.Error(result.error().message)))
                    logger.logD("Failed to set user ${result.error()}")
                }
            }
        // _events.postValue(Event(UiEvent.RedirectToChannels))
    }

    private fun observeChannels() {
        channelListViewModel.state.observe(this) {
            if (!it.isLoading && it.channels.isNotEmpty() && !isFirstTimeSetupDone) {
                homeViewModel.submitAction(HomeViewModel.Action.SelectChannelAction(it.channels[0].cid))
                isFirstTimeSetupDone = true
            }
        }
    }
}