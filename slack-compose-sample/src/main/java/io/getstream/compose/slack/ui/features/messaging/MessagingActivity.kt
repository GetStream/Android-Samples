package io.getstream.compose.slack.ui.features.messaging

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.CompositionLocalProvider
import io.getstream.chat.android.compose.ui.theme.ChatTheme
import io.getstream.compose.slack.shapes

class MessagingActivity : ComponentActivity() {
    private var channelId: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        handleExtras()
        setContent {
            CompositionLocalProvider(
                LocalBackPressedDispatcher provides this.onBackPressedDispatcher
            ) {
                ChatTheme(shapes = shapes()) {
                    SlackMessageScreen(
                        channelId = channelId,
                        onBackPressed = { finish() }
                    )
                }
            }
        }
    }

    private fun handleExtras() {
        intent.getStringExtra(KEY_CHANNEL_ID)?.let {
            channelId = it
        }
    }

    companion object {
        private const val KEY_CHANNEL_ID = "channel_id"

        fun getIntent(channelId: String, context: Context): Intent {
            return Intent(context, MessagingActivity::class.java).apply {
                putExtra(KEY_CHANNEL_ID, channelId)
            }
        }
    }
}
