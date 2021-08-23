package io.getstream.compose.slack.ui.features.messaging

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import io.getstream.chat.android.compose.ui.theme.ChatTheme
import io.getstream.compose.slack.shapes

class MessagingActivity : ComponentActivity() {
    private var channelId: String = ""
    private var screenTitle: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        handleExtras()
        setContent {
           ChatTheme(shapes = shapes()) {
                ChannelMessagingScreen(
                    title = screenTitle,
                    channelId = channelId,
                    onBackPressed = { finish() }
                )
            }
        }
    }

    private fun handleExtras() {
        intent.getStringExtra(KEY_SCREEN_TITLE)?.let {
            screenTitle = it
        }
        intent.getStringExtra(KEY_CHANNEL_ID)?.let {
            channelId = it
        }
    }

    companion object {
        private const val KEY_SCREEN_TITLE = "screen_title"
        private const val KEY_CHANNEL_ID = "channel_id"

        fun getIntent(channelId: String, screenTitle: String, context: Context): Intent {
            return Intent(context, MessagingActivity::class.java).apply {
                putExtra(KEY_SCREEN_TITLE, screenTitle)
                putExtra(KEY_CHANNEL_ID, channelId)
            }
        }
    }
}
