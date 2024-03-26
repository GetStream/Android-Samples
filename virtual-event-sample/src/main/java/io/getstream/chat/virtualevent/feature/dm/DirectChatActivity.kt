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

package io.getstream.chat.virtualevent.feature.dm

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import androidx.activity.OnBackPressedCallback
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.descendants
import androidx.core.view.setPadding
import io.getstream.chat.android.ui.common.state.messages.Edit
import io.getstream.chat.android.ui.common.state.messages.MessageMode
import io.getstream.chat.android.ui.feature.messages.composer.MessageComposerView
import io.getstream.chat.android.ui.feature.messages.header.MessageListHeaderView
import io.getstream.chat.android.ui.feature.messages.list.MessageListView
import io.getstream.chat.android.ui.viewmodel.messages.MessageComposerViewModel
import io.getstream.chat.android.ui.viewmodel.messages.MessageListHeaderViewModel
import io.getstream.chat.android.ui.viewmodel.messages.MessageListViewModel
import io.getstream.chat.android.ui.viewmodel.messages.MessageListViewModelFactory
import io.getstream.chat.android.ui.viewmodel.messages.bindView
import io.getstream.chat.virtualevent.databinding.ActivityDirectChatBinding

class DirectChatActivity : AppCompatActivity() {

    private val cid: String by lazy {
        intent.getStringExtra(KEY_EXTRA_CID)!!
    }

    private val factory: MessageListViewModelFactory by lazy { MessageListViewModelFactory(this, cid) }
    private val messageListViewModel: MessageListViewModel by viewModels { factory }
    private val messageListHeaderViewModel: MessageListHeaderViewModel by viewModels { factory }
    private val messageInputViewModel: MessageComposerViewModel by viewModels { factory }

    private lateinit var binding: ActivityDirectChatBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDirectChatBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupMessageListHeader(binding.messageListHeaderView)
        setupMessageList(binding.messageListView)
        setupMessageInput(binding.messageInputView)
    }

    private fun setupMessageListHeader(messageListHeaderView: MessageListHeaderView) {
        with(messageListHeaderView) {
            messageListHeaderViewModel.bindView(this, this@DirectChatActivity)

            val backHandler = {
                messageListViewModel.onEvent(MessageListViewModel.Event.BackButtonPressed)
            }
            setBackButtonClickListener(backHandler)
            onBackPressedDispatcher.addCallback(
                this@DirectChatActivity,
                object : OnBackPressedCallback(true) {
                    override fun handleOnBackPressed() {
                        backHandler()
                    }
                }
            )

            // Workaround to remove back button padding
            messageListHeaderView.descendants
                .firstOrNull { it is ImageView }
                ?.let {
                    it.setPadding(0)
                    it.background = null
                }
        }
    }

    private fun setupMessageList(messageListView: MessageListView) {
        messageListViewModel.bindView(messageListView, this@DirectChatActivity)

        messageListViewModel.state.observe(this@DirectChatActivity) {
            when (it) {
                is MessageListViewModel.State.Loading -> Unit
                is MessageListViewModel.State.Result -> Unit
                is MessageListViewModel.State.NavigateUp -> finish()
            }
        }
    }

    private fun setupMessageInput(messageInputView: MessageComposerView) {
        messageInputViewModel.apply {
            messageInputViewModel.bindView(messageInputView, this@DirectChatActivity)

            messageListViewModel.mode.observe(this@DirectChatActivity) {
                when (it) {
                    is MessageMode.MessageThread -> {
                        messageListHeaderViewModel.setActiveThread(it.parentMessage)
                    }
                    is MessageMode.Normal -> {
                        messageListHeaderViewModel.resetThread()
                    }
                }
                messageInputViewModel.setMessageMode(it)
            }
            binding.messageListView.setMessageEditHandler {
                performMessageAction(Edit(it))
            }
        }
    }

    companion object {
        private const val KEY_EXTRA_CID: String = "extra_cid"

        fun createIntent(context: Context, cid: String): Intent {
            return Intent(context, DirectChatActivity::class.java).putExtra(KEY_EXTRA_CID, cid)
        }
    }
}
