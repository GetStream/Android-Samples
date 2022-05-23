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

            // 1. Build a date picker
            val dialogPickerDialog = MaterialDatePicker.Builder
                .datePicker()
                .build()

            // 2. Add a listener that will add a date attachment on positive click
            dialogPickerDialog.addOnPositiveButtonClickListener {
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

            // 3. Show the date picker dialog
            messageInputView.setAttachmentButtonClickListener {
                dialogPickerDialog.show(requireActivity().supportFragmentManager, null)
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
