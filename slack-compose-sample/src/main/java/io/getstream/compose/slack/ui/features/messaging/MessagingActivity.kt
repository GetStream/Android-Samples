package io.getstream.compose.slack.ui.features.messaging

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import io.getstream.chat.android.compose.ui.theme.ChatTheme

class MessagingActivity : ComponentActivity() {
    private lateinit var screenTitle: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        handleExtras()
        setContent {
            ChatTheme {
                ChannelMessagingScreen(
                    title = screenTitle
                ) {
                    finish()
                }
            }
        }
    }

    private fun handleExtras() {
        screenTitle = intent.getStringExtra(KEY_SCREEN_TITLE) ?: "Message"
    }

    companion object {
        private const val KEY_SCREEN_TITLE = "screen_title"

        fun getIntent(screenTitle: String, context: Context): Intent {
            return Intent(context, MessagingActivity::class.java).apply {
                putExtra(KEY_SCREEN_TITLE, screenTitle)
            }
        }
    }
}
