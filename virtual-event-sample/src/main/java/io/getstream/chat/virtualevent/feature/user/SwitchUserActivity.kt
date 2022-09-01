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

 *  The above copyright notice and this permission notice shall be included in
 *  all copies or substantial portions of the Software.

 *  THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 *   IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 *  FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 *  AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 *  LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 *  OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 *  THE SOFTWARE.
 */

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
