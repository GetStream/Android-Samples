package io.getstream.chat.virtualevent.feature.dm.start

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import io.getstream.chat.android.livedata.utils.EventObserver
import io.getstream.chat.virtualevent.databinding.ActivityStartDirectChatBinding
import io.getstream.chat.virtualevent.feature.dm.DirectChatActivity
import io.getstream.chat.virtualevent.util.setupToolbar

/**
 * Activity that shows a list of available users and allows to start
 * a new direct (1-to-1) conversation.
 */
class StartDirectChatActivity : AppCompatActivity() {

    private val viewModel: StartDirectChatViewModel by viewModels()
    private val adapter: ParticipantListAdapter = ParticipantListAdapter {
        viewModel.onUserSelected(it)
    }

    private lateinit var binding: ActivityStartDirectChatBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStartDirectChatBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupToolbar(binding.toolbar)
        binding.usersRecyclerView.adapter = adapter
        with(viewModel) {
            state.observe(this@StartDirectChatActivity, ::renderUsersState)
            events.observe(this@StartDirectChatActivity, EventObserver(::handleEvent))
        }
    }

    private fun renderUsersState(state: StartDirectChatViewModel.State) {
        when (state) {
            is StartDirectChatViewModel.State.Content -> adapter.setUsers(state.users)
        }
    }

    private fun handleEvent(event: StartDirectChatViewModel.UiEvent) {
        when (event) {
            is StartDirectChatViewModel.UiEvent.NavigateToChat -> {
                startActivity(DirectChatActivity.createIntent(this, event.cid))
            }
        }
    }

    companion object {
        fun createIntent(context: Context): Intent {
            return Intent(context, StartDirectChatActivity::class.java)
        }
    }
}
