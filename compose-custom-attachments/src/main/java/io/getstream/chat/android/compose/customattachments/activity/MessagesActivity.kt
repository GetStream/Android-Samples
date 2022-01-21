package io.getstream.chat.android.compose.customattachments.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import io.getstream.chat.android.compose.customattachments.ui.CustomMessagesScreen
import io.getstream.chat.android.compose.customattachments.ui.dateAttachmentFactory
import io.getstream.chat.android.compose.ui.attachments.StreamAttachmentFactories
import io.getstream.chat.android.compose.ui.theme.ChatTheme

class MessagesActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val channelId = requireNotNull(intent.getStringExtra(KEY_CHANNEL_ID))

        val customFactories = listOf(dateAttachmentFactory)
        val defaultFactories = StreamAttachmentFactories.defaultFactories()

        setContent {
            ChatTheme(attachmentFactories = customFactories + defaultFactories) {
                CustomMessagesScreen(
                    channelId = channelId,
                    onBackPressed = { finish() }
                )
            }
        }
    }

    companion object {
        private const val KEY_CHANNEL_ID = "channelId"

        fun getIntent(context: Context, channelId: String): Intent {
            return Intent(context, MessagesActivity::class.java).apply {
                putExtra(KEY_CHANNEL_ID, channelId)
            }
        }
    }
}
