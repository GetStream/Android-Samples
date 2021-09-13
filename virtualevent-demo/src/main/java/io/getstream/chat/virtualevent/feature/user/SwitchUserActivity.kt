package io.getstream.chat.virtualevent.feature.user

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import io.getstream.chat.android.livedata.utils.EventObserver
import io.getstream.chat.virtualevent.MainActivity
import io.getstream.chat.virtualevent.databinding.ActivitySwitchUserBinding
import io.getstream.chat.virtualevent.util.setupToolbar

class SwitchUserActivity : AppCompatActivity() {

    private val viewModel: SwitchUserViewModel by viewModels()
    private val adapter: UserListAdapter = UserListAdapter {
        viewModel.onUserSelected(it)
    }

    private lateinit var binding: ActivitySwitchUserBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySwitchUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupToolbar(binding.toolbar)
        binding.usersRecyclerView.adapter = adapter
        with(viewModel) {
            state.observe(this@SwitchUserActivity, ::renderUsersState)
            events.observe(this@SwitchUserActivity, EventObserver(::handleEvent))
        }
    }

    private fun renderUsersState(state: SwitchUserViewModel.State) {
        when (state) {
            is SwitchUserViewModel.State.Content -> adapter.setUsers(state.users)
        }
    }

    private fun handleEvent(event: SwitchUserViewModel.UiEvent) {
        when (event) {
            is SwitchUserViewModel.UiEvent.NavigateToHomeScreen -> {
                startActivity(MainActivity.createIntent(this))
                finish()
            }
        }
    }

    companion object {
        fun createIntent(context: Context): Intent {
            return Intent(context, SwitchUserActivity::class.java)
        }
    }
}
