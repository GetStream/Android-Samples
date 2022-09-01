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

package io.getstream.chat.android.customattachments.activity

import android.content.Context
import android.content.Intent
import com.google.android.material.datepicker.MaterialDatePicker
import io.getstream.chat.android.client.models.Attachment
import io.getstream.chat.android.customattachments.R
import io.getstream.chat.android.customattachments.factory.DateAttachmentPreviewFactory
import io.getstream.chat.android.ui.message.MessageListActivity
import io.getstream.chat.android.ui.message.MessageListFragment
import io.getstream.chat.android.ui.message.input.MessageInputView
import java.text.DateFormat
import java.util.Date

class MessagesActivity : MessageListActivity() {

    override fun createMessageListFragment(cid: String, messageId: String?): MessageListFragment {
        return MessageListFragment.newInstance(cid) {
            setFragment(CustomMessageListFragment())
            customTheme(R.style.CustomStreamUiTheme)
            showHeader(true)
            messageId(messageId)
        }
    }

    class CustomMessageListFragment : MessageListFragment() {

        override fun setupMessageInput(messageInputView: MessageInputView) {
            super.setupMessageInput(messageInputView)

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
                messageInputView.submitCustomAttachments(
                    attachments = listOf(attachment),
                    viewHolderFactory = DateAttachmentPreviewFactory()
                )
            }

            // Show the date picker dialog when the attachment button is clicked
            messageInputView.setAttachmentButtonClickListener {
                datePickerDialog.show(requireActivity().supportFragmentManager, null)
            }
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
