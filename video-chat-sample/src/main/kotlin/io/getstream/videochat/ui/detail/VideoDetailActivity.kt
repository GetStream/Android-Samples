package io.getstream.videochat.ui.detail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import android.widget.ImageView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.RecyclerView
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.YouTubePlayerCallback
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView
import io.getstream.videochat.R
import io.getstream.videochat.YoutubeVideo

class VideoDetailActivity : AppCompatActivity(R.layout.activity_video_detail) {

    private val video by lazy { intent.getSerializableExtra(EXTRA_VIDEO) as YoutubeVideo }

    private val viewModel: VideoDetailViewModel by viewModels {
        VideoDetailViewModelFactory(video)
    }

    private lateinit var inputEditText: EditText
    private lateinit var sendMessageButton: ImageView
    private lateinit var messagesRecyclerView: RecyclerView

    private val messageListAdapter: MessageListAdapter = MessageListAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupYoutubePlayer()
        setupMessageList()
        setupMessageInput()
    }

    private fun setupYoutubePlayer() {
        findViewById<YouTubePlayerView>(R.id.videoPlayerView).apply {
            lifecycle.addObserver(this)
            getYouTubePlayerWhenReady(object : YouTubePlayerCallback {
                override fun onYouTubePlayer(youTubePlayer: YouTubePlayer) {
                    youTubePlayer.loadVideo(video.id, 0f)
                }
            })
        }
    }

    private fun setupMessageList() {
        messagesRecyclerView = findViewById<RecyclerView>(R.id.messagesRecyclerView).apply {
            adapter = messageListAdapter
        }
        viewModel.messages.observe(this) { messages ->
            messageListAdapter.submitList(messages) {
                messagesRecyclerView.post {
                    messagesRecyclerView.smoothScrollToPosition(messages.size - 1)
                }
            }
        }
    }

    private fun setupMessageInput() {
        inputEditText = findViewById<EditText>(R.id.inputEditText).apply {
            addTextChangedListener {
                sendMessageButton.isEnabled = it?.isNotBlank() ?: false
            }
        }
        sendMessageButton = findViewById<ImageView>(R.id.sendButton).apply {
            setOnClickListener {
                inputEditText.text
                    ?.toString()
                    ?.takeIf { it.isNotBlank() }
                    ?.let {
                        inputEditText.setText("")
                        viewModel.sendMessage(it)
                    }
            }
        }
    }

    companion object {
        private const val EXTRA_VIDEO = "extra_video"

        fun createIntent(context: Context, video: YoutubeVideo): Intent {
            return Intent(context, VideoDetailActivity::class.java).apply {
                putExtra(EXTRA_VIDEO, video)
            }
        }
    }
}
