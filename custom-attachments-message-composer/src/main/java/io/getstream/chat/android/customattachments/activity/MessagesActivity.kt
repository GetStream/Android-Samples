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

package io.getstream.chat.android.customattachments.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import androidx.activity.addCallback
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.google.android.material.datepicker.MaterialDatePicker
import io.getstream.chat.android.core.ExperimentalStreamChatApi
import io.getstream.chat.android.customattachments.databinding.ActivityMessagesBinding
import io.getstream.chat.android.customattachments.databinding.CustomMessageComposerLeadingContentBinding
import io.getstream.chat.android.models.Attachment
import io.getstream.chat.android.models.ChannelCapabilities
import io.getstream.chat.android.ui.common.state.messages.Edit
import io.getstream.chat.android.ui.common.state.messages.MessageMode
import io.getstream.chat.android.ui.common.state.messages.Reply
import io.getstream.chat.android.ui.common.state.messages.composer.MessageComposerState
import io.getstream.chat.android.ui.feature.messages.composer.MessageComposerContext
import io.getstream.chat.android.ui.feature.messages.composer.MessageComposerViewStyle
import io.getstream.chat.android.ui.feature.messages.composer.content.MessageComposerContent
import io.getstream.chat.android.ui.viewmodel.messages.MessageComposerViewModel
import io.getstream.chat.android.ui.viewmodel.messages.MessageListHeaderViewModel
import io.getstream.chat.android.ui.viewmodel.messages.MessageListViewModel
import io.getstream.chat.android.ui.viewmodel.messages.MessageListViewModelFactory
import io.getstream.chat.android.ui.viewmodel.messages.bindView
import java.text.DateFormat
import java.util.Date

class MessagesActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMessagesBinding

    private val factory: MessageListViewModelFactory by lazy {
        MessageListViewModelFactory(this, requireNotNull(intent.getStringExtra(EXTRA_CID)))
    }
    private val messageListHeaderViewModel: MessageListHeaderViewModel by viewModels { factory }
    private val messageListViewModel: MessageListViewModel by viewModels { factory }
    private val messageComposerViewModel: MessageComposerViewModel by viewModels { factory }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMessagesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        messageListHeaderViewModel.bindView(binding.messageListHeaderView, this)
        messageListViewModel.bindView(binding.messageListView, this)
        messageComposerViewModel.bindView(binding.messageComposerView, this)

        messageListViewModel.mode.observe(this) {
            when (it) {
                is MessageMode.MessageThread -> {
                    messageListHeaderViewModel.setActiveThread(it.parentMessage)
                    messageComposerViewModel.setMessageMode(MessageMode.MessageThread(it.parentMessage))
                }
                is MessageMode.Normal -> {
                    messageListHeaderViewModel.resetThread()
                    messageComposerViewModel.leaveThread()
                }
            }
        }
        binding.messageListView.setMessageReplyHandler { _, message ->
            messageComposerViewModel.performMessageAction(Reply(message))
        }
        binding.messageListView.setMessageEditHandler { message ->
            messageComposerViewModel.performMessageAction(Edit(message))
        }

        messageListViewModel.state.observe(this) { state ->
            if (state is MessageListViewModel.State.NavigateUp) {
                finish()
            }
        }

        val backHandler = {
            messageListViewModel.onEvent(MessageListViewModel.Event.BackButtonPressed)
        }
        binding.messageListHeaderView.setBackButtonClickListener(backHandler)
        onBackPressedDispatcher.addCallback(this) {
            backHandler()
        }

        // Set custom leading content view
        binding.messageComposerView.setLeadingContent(
            CustomMessageComposerLeadingContent(this).also {
                it.attachmentsButtonClickListener =
                    { binding.messageComposerView.attachmentsButtonClickListener() }
                it.commandsButtonClickListener =
                    { binding.messageComposerView.commandsButtonClickListener() }
                it.calendarButtonClickListener = {
                    // Create an instance of a date picker dialog
                    val datePickerDialog = MaterialDatePicker.Builder
                        .datePicker()
                        .build()

                    // Add an attachment to the message input when the user selects a date
                    datePickerDialog.addOnPositiveButtonClickListener {
                        val date = DateFormat
                            .getDateInstance(DateFormat.LONG)
                            .format(Date(it))
                        val attachment = Attachment(
                            type = "date",
                            extraData = mutableMapOf("payload" to date)
                        )
                        messageComposerViewModel.addSelectedAttachments(listOf(attachment))
                    }

                    // Show the date picker dialog when the attachment button is clicked
                    datePickerDialog.show(supportFragmentManager, null)
                }
            }
        )
    }

    @OptIn(ExperimentalStreamChatApi::class)
    private class CustomMessageComposerLeadingContent : FrameLayout, MessageComposerContent {

        private lateinit var binding: CustomMessageComposerLeadingContentBinding
        private lateinit var style: MessageComposerViewStyle

        var attachmentsButtonClickListener: () -> Unit = {}
        var commandsButtonClickListener: () -> Unit = {}

        // Click listener for the date picker button
        var calendarButtonClickListener: () -> Unit = {}

        constructor(context: Context) : this(context, null)

        constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

        constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
            context,
            attrs,
            defStyleAttr
        ) {
            binding = CustomMessageComposerLeadingContentBinding.inflate(
                LayoutInflater.from(context),
                this
            )
            binding.attachmentsButton.setOnClickListener { attachmentsButtonClickListener() }
            binding.commandsButton.setOnClickListener { commandsButtonClickListener() }

            // Set click listener for the date picker button
            binding.calendarButton.setOnClickListener { calendarButtonClickListener() }
        }

        override fun attachContext(messageComposerContext: MessageComposerContext) {
            this.style = messageComposerContext.style
        }

        override fun renderState(state: MessageComposerState) {
            val canSendMessage = state.ownCapabilities.contains(ChannelCapabilities.SEND_MESSAGE)
            val canUploadFile = state.ownCapabilities.contains(ChannelCapabilities.UPLOAD_FILE)
            val hasTextInput = state.inputValue.isNotEmpty()
            val hasAttachments = state.attachments.isNotEmpty()
            val hasCommandInput = state.inputValue.startsWith("/")
            val hasCommandSuggestions = state.commandSuggestions.isNotEmpty()
            val hasMentionSuggestions = state.mentionSuggestions.isNotEmpty()
            val isInEditMode = state.action is Edit

            binding.attachmentsButton.isEnabled =
                !hasCommandInput && !hasCommandSuggestions && !hasMentionSuggestions
            binding.attachmentsButton.isVisible =
                style.attachmentsButtonVisible && canSendMessage && canUploadFile && !isInEditMode

            binding.commandsButton.isEnabled = !hasTextInput && !hasAttachments
            binding.commandsButton.isVisible =
                style.commandsButtonVisible && canSendMessage && !isInEditMode
            binding.commandsButton.isSelected = hasCommandSuggestions
        }
    }

    companion object {
        private const val EXTRA_CID: String = "extra_cid"

        fun createIntent(context: Context, cid: String): Intent {
            return Intent(context, MessagesActivity::class.java).apply {
                putExtra(EXTRA_CID, cid)
            }
        }
    }
}
