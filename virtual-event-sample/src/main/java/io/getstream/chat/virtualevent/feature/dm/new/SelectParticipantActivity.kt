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

package io.getstream.chat.virtualevent.feature.dm.new

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import io.getstream.chat.android.livedata.utils.EventObserver
import io.getstream.chat.virtualevent.databinding.ActivitySelectParticipantBinding
import io.getstream.chat.virtualevent.feature.dm.DirectChatActivity
import io.getstream.chat.virtualevent.util.setupToolbar

/**
 * Activity that shows a list of available users and allows to start
 * a new direct (1-to-1) conversation.
 */
class SelectParticipantActivity : AppCompatActivity() {

    private val viewModel: SelectParticipantViewModel by viewModels()
    private val adapter: ParticipantListAdapter = ParticipantListAdapter {
        viewModel.onUserSelected(it)
    }

    private lateinit var binding: ActivitySelectParticipantBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySelectParticipantBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupToolbar(binding.toolbar)
        binding.participantsRecyclerView.adapter = adapter
        with(viewModel) {
            state.observe(this@SelectParticipantActivity, ::renderParticipantsState)
            events.observe(this@SelectParticipantActivity, EventObserver(::handleEvent))
        }
    }

    private fun renderParticipantsState(state: SelectParticipantViewModel.State) {
        when (state) {
            is SelectParticipantViewModel.State.Content -> adapter.setParticipants(state.participants)
            else -> {}
        }
    }

    private fun handleEvent(event: SelectParticipantViewModel.UiEvent) {
        when (event) {
            is SelectParticipantViewModel.UiEvent.NavigateToChat -> {
                startActivity(DirectChatActivity.createIntent(this, event.cid))
                finish()
            }
        }
    }

    companion object {
        fun createIntent(context: Context): Intent {
            return Intent(context, SelectParticipantActivity::class.java)
        }
    }
}
