package io.getstream.livestream.compose.ui

import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import io.getstream.chat.android.client.ChatClient
import io.getstream.chat.android.compose.ui.theme.ChatTheme
import io.getstream.chat.android.compose.viewmodel.messages.MessageComposerViewModel
import io.getstream.chat.android.compose.viewmodel.messages.MessageListViewModel
import io.getstream.chat.android.compose.viewmodel.messages.MessagesViewModelFactory
import io.getstream.chat.android.offline.ChatDomain
import io.getstream.livestream.compose.R
import io.getstream.livestream.compose.darkColorPalette
import io.getstream.livestream.compose.lightColorPalette
import io.getstream.livestream.compose.models.LiveStreamType
import io.getstream.livestream.compose.models.LiveStreamType.Youtube
import io.getstream.livestream.compose.shapes
import io.getstream.livestream.compose.streams.RenderLiveStream

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
    private var channelId: String = ""

    private val composerViewModel by viewModels<MessageComposerViewModel>(factoryProducer = { factory })
    private val listViewModel by viewModels<MessageListViewModel>(factoryProducer = { factory })
    private var liveStreamType: LiveStreamType = Youtube
    private lateinit var sharedPref: SharedPreferences

    @ExperimentalPermissionsApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedPref =
            getSharedPreferences(getString(R.string.name_shared_pref), Context.MODE_PRIVATE)
        handleExtras()

        setContent {
            val isDarkMode by remember {
                mutableStateOf(
                    sharedPref.getBoolean(
                        getString(R.string.key_theme),
                        false
                    )
                )
            }

            ChatTheme(
                colors = if (isDarkMode) darkColorPalette() else lightColorPalette(),
                shapes = shapes()
            ) {
                RenderLiveStream(
                    liveStreamType = liveStreamType,
                    composerViewModel = composerViewModel,
                    listViewModel = listViewModel,
                    channelId = channelId
                ) {
                    // On back press
                    finish()
                }
            }
        }
    }

    private fun handleExtras() {
        if (intent.hasExtra(KEY_LIVE_STREAM_TYPE)) {
            liveStreamType =
                intent.getSerializableExtra(KEY_LIVE_STREAM_TYPE) as LiveStreamType
        }
        intent.getStringExtra(KEY_CHANNEL_ID)?.let {
            channelId = it
        }
    }

    companion object {
        private const val KEY_CHANNEL_ID = "channelId"
        private const val KEY_LIVE_STREAM_TYPE = "liveStreamType"

        fun getIntent(context: Context, channelId: String, liveStreamType: LiveStreamType): Intent {
            return Intent(context, LiveStreamActivity::class.java).apply {
                putExtra(KEY_CHANNEL_ID, channelId)
                putExtra(KEY_LIVE_STREAM_TYPE, liveStreamType)
            }
        }
    }
}
