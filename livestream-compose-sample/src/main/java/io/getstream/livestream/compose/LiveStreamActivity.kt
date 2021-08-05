package io.getstream.livestream.compose

import android.Manifest
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import io.getstream.chat.android.client.ChatClient
import io.getstream.chat.android.compose.ui.theme.ChatTheme
import io.getstream.chat.android.compose.viewmodel.messages.MessageComposerViewModel
import io.getstream.chat.android.compose.viewmodel.messages.MessageListViewModel
import io.getstream.chat.android.compose.viewmodel.messages.MessagesViewModelFactory
import io.getstream.chat.android.offline.ChatDomain
import io.getstream.livestream.compose.streams.CameraLiveStream
import io.getstream.livestream.compose.streams.VideoLiveStream
import io.getstream.livestream.compose.streams.YoutubeLiveStream

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
    private var liveStreamType: LiveStreamType = LiveStreamType.Youtube

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        handleExtras()
        setViewContent()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<String>, grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_CODE_PERMISSIONS) {
            if (allPermissionsGranted()) {
                setViewContent()
            } else {
                Toast.makeText(
                    this,
                    getString(R.string.permission_message),
                    Toast.LENGTH_SHORT
                ).show()
                finish()
            }
        }
    }

    private fun setViewContent() {
        setContent {
            ChatTheme {
                when (liveStreamType) {
                    LiveStreamType.Youtube -> {
                        YoutubeLiveStream(
                            composerViewModel,
                            listViewModel
                        )
                    }
                    LiveStreamType.Camera -> {
                        if (allPermissionsGranted()) {
                            CameraLiveStream(
                                composerViewModel,
                                listViewModel
                            )
                        } else {
                            ActivityCompat.requestPermissions(
                                this, REQUIRED_PERMISSIONS, REQUEST_CODE_PERMISSIONS
                            )
                        }
                    }
                    LiveStreamType.Video -> {
                        VideoLiveStream(
                            urlToLoad = "asset:///video.mp4",
                            composerViewModel,
                            listViewModel
                        )
                    }
                }
            }
        }
    }

    private fun handleExtras() {
        if (intent.hasExtra(KEY_LIVE_STREAM_TYPE)) {
            liveStreamType =
                intent.getSerializableExtra(KEY_LIVE_STREAM_TYPE) as LiveStreamType
        }
    }

    private fun allPermissionsGranted() = REQUIRED_PERMISSIONS.all {
        ContextCompat.checkSelfPermission(
            baseContext, it
        ) == PackageManager.PERMISSION_GRANTED
    }


    companion object {
        private const val KEY_CHANNEL_ID = "channelId"
        private const val KEY_LIVE_STREAM_TYPE = "liveStreamType"

        private const val REQUEST_CODE_PERMISSIONS = 10
        private val REQUIRED_PERMISSIONS = arrayOf(Manifest.permission.CAMERA)

        fun getIntent(context: Context, channelId: String, liveStreamType: LiveStreamType): Intent {
            return Intent(context, LiveStreamActivity::class.java).apply {
                putExtra(KEY_CHANNEL_ID, channelId)
                putExtra(KEY_LIVE_STREAM_TYPE, liveStreamType)
            }
        }
    }
}
