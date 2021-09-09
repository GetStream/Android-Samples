package io.getstream.chat.virtualevent.feature.dm

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import io.getstream.chat.android.livedata.utils.EventObserver
import io.getstream.chat.virtualevent.databinding.ActivitySelectUserBinding

class SelectUserActivity : AppCompatActivity() {

    private val viewModel: SelectUserViewModel by viewModels()
    private val adapter: UserListAdapter = UserListAdapter {
        viewModel.onUserSelected(it)
    }

    private lateinit var binding: ActivitySelectUserBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySelectUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.usersRecyclerView.adapter = adapter
        with(viewModel) {
            state.observe(this@SelectUserActivity, ::renderUsersState)
            events.observe(this@SelectUserActivity, EventObserver(::handleEvent))
        }
    }

    private fun renderUsersState(state: SelectUserViewModel.State) {
        when (state) {
            is SelectUserViewModel.State.Content -> adapter.setUsers(state.users)
        }
    }

    private fun handleEvent(event: SelectUserViewModel.UiEvent) {
        when (event) {
            is SelectUserViewModel.UiEvent.NavigateToChat -> {
                startActivity(DmActivity.createIntent(this, event.cid))
            }
        }
    }

    companion object {
        fun createIntent(context: Context): Intent {
            return Intent(context, SelectUserActivity::class.java)
        }
    }
}
