package io.getstream.compose.slack.ui.features.messaging

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.runtime.CompositionLocalProvider
import io.getstream.chat.android.compose.ui.theme.ChatTheme
import io.getstream.compose.slack.shapes

class MessagingActivity : ComponentActivity() {
    private var channelId: String = ""
    private var screenTitle: String = ""

    @ExperimentalFoundationApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        handleExtras()
        setContent {
            CompositionLocalProvider(
                LocalBackPressedDispatcher provides this.onBackPressedDispatcher
            ) {
                ChatTheme(shapes = shapes()) {
                    ChannelMessagingScreen(
                        title = screenTitle, // TODO remove this attr cause VM can do this change inside screen directly
                        channelId = channelId,
                        onBackPressed = { finish() }
                    )
                }
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
        private const val KEY_SCREEN_TITLE = "screen_title" // TODO we should not pass in this - use MessageLVM
        private const val KEY_CHANNEL_ID = "channel_id"

        fun getIntent(channelId: String, screenTitle: String, context: Context): Intent {
            return Intent(context, MessagingActivity::class.java).apply {
                putExtra(KEY_SCREEN_TITLE, screenTitle)
                putExtra(KEY_CHANNEL_ID, channelId)
            }
        }
    }
}
