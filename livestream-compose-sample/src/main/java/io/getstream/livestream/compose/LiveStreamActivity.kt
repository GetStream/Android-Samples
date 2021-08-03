package io.getstream.livestream.compose

import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import io.getstream.chat.android.client.ChatClient
import io.getstream.chat.android.compose.viewmodel.messages.MessageComposerViewModel
import io.getstream.chat.android.compose.viewmodel.messages.MessageListViewModel
import io.getstream.chat.android.compose.viewmodel.messages.MessagesViewModelFactory
import io.getstream.chat.android.offline.ChatDomain
import io.getstream.livestream.compose.ui.theme.AndroidSamplesTheme

class LiveStreamActivity : ComponentActivity() {

    private val factory by lazy {
        MessagesViewModelFactory(
            this,
            getSystemService(CLIPBOARD_SERVICE) as ClipboardManager,
            ChatClient.instance(),
            ChatDomain.instance(),
            intent.getStringExtra(KEY_CHANNEL_ID) ?: "",
            30
        )
    }

    private val composerViewModel by viewModels<MessageComposerViewModel>(factoryProducer = { factory })
    private val listViewModel by viewModels<MessageListViewModel>(factoryProducer = { factory })

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AndroidSamplesTheme(darkTheme = true) {
                Surface(color = MaterialTheme.colors.background) {
                    LiveStreamScreen(composerViewModel, listViewModel)
                }
            }
        }
    }

    companion object {
        private const val KEY_CHANNEL_ID = "channelId"

        fun getIntent(context: Context, channelId: String): Intent {
            return Intent(context, LiveStreamActivity::class.java).apply {
                putExtra(KEY_CHANNEL_ID, channelId)
            }
        }
    }
}
