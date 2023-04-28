/*
 *  The MIT License (MIT)
 *
 *  Copyright 2022 Stream.IO, Inc. All Rights Reserved.
 *
 *  Permission is hereby granted, free of charge, to any person obtaining a copy
 *  of this software and associated documentation files (the "Software"), to deal
 *  in the Software without restriction, including without limitation the rights
 *   to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 *   copies of the Software, and to permit persons to whom the Software is
 *  furnished to do so, subject to the following conditions:
 *
 *  The above copyright notice and this permission notice shall be included in
 *  all copies or substantial portions of the Software.
 *
 *  THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 *   IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 *  FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 *  AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 *  LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 *  OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 *  THE SOFTWARE.
 */

package io.getstream.chat.virtualevent.feature.dm.new

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
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
            events.observe(this@SelectParticipantActivity, Observer(::handleEvent))
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
