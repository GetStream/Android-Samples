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
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import androidx.core.view.isVisible
import com.google.android.material.datepicker.MaterialDatePicker
import io.getstream.chat.android.customattachments.databinding.CustomMessageComposerLeadingContentBinding
import io.getstream.chat.android.models.Attachment
import io.getstream.chat.android.models.ChannelCapabilities
import io.getstream.chat.android.ui.common.state.messages.Edit
import io.getstream.chat.android.ui.common.state.messages.composer.MessageComposerState
import io.getstream.chat.android.ui.feature.messages.MessageListActivity
import io.getstream.chat.android.ui.feature.messages.MessageListFragment
import io.getstream.chat.android.ui.feature.messages.composer.MessageComposerContext
import io.getstream.chat.android.ui.feature.messages.composer.MessageComposerView
import io.getstream.chat.android.ui.feature.messages.composer.MessageComposerViewStyle
import io.getstream.chat.android.ui.feature.messages.composer.content.MessageComposerContent
import java.text.SimpleDateFormat
import java.util.Date

class MessagesActivity : MessageListActivity() {

    override fun createMessageListFragment(cid: String, messageId: String?): MessageListFragment {
        return MessageListFragment.newInstance(cid) {
            setFragment(CustomMessageListFragment())
            showHeader(true)
            messageId(messageId)
        }
    }

    class CustomMessageListFragment : MessageListFragment() {

        override fun setupMessageComposer(messageComposerView: MessageComposerView) {
            super.setupMessageComposer(messageComposerView)
            messageComposerView.setLeadingContent(
                CustomMessageComposerLeadingContent(messageComposerView.context).also {
                    it.attachmentsButtonClickListener = { binding.messageComposerView.attachmentsButtonClickListener() }
                    it.commandsButtonClickListener = { binding.messageComposerView.commandsButtonClickListener() }
                    it.calendarButtonClickListener = {
                        // Create an instance of a date picker dialog
                        val datePickerDialog = MaterialDatePicker.Builder
                            .datePicker()
                            .build()

                        // Add an attachment to the message input when the user selects a date
                        datePickerDialog.addOnPositiveButtonClickListener { date ->
                            val payload = SimpleDateFormat("MMMM dd, yyyy").format(Date(date))
                            val attachment = Attachment(
                                type = "date",
                                extraData = mutableMapOf("payload" to payload)
                            )
                            messageComposerViewModel.addSelectedAttachments(listOf(attachment))
                        }

                        // Show the date picker dialog at the click of the calendar button
                        datePickerDialog.show(requireActivity().supportFragmentManager, null)
                    }
                }
            )
        }
    }
    private class CustomMessageComposerLeadingContent : FrameLayout, MessageComposerContent {

        private val binding: CustomMessageComposerLeadingContentBinding
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
            binding = CustomMessageComposerLeadingContentBinding.inflate(LayoutInflater.from(context), this)
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
            binding.commandsButton.isVisible = style.commandsButtonVisible && canSendMessage && !isInEditMode
            binding.commandsButton.isSelected = hasCommandSuggestions
        }
    }
    companion object {
        private const val EXTRA_CID: String = "extra_cid"
        private const val EXTRA_MESSAGE_ID: String = "extra_message_id"

        fun createIntent(context: Context, cid: String, messageId: String? = null): Intent {
            return Intent(context, MessagesActivity::class.java).apply {
                putExtra(EXTRA_CID, cid)
                putExtra(EXTRA_MESSAGE_ID, messageId)
            }
        }
    }
}
