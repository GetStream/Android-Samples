package io.getstream.livestream.ui

import android.os.Bundle
import android.util.DisplayMetrics
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearSmoothScroller
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.options.IFramePlayerOptions
import io.getstream.chat.android.client.models.Message
import io.getstream.livestream.R
import io.getstream.livestream.databinding.ActivityMainBinding

class LiveStreamActivity : AppCompatActivity(R.layout.activity_main) {

    private lateinit var binding: ActivityMainBinding

    private val viewModel: LiveStreamViewModel by viewModels()

    private val adapter = MessageListAdapter()

    private val messageListSmoothScroller by lazy {
        object : LinearSmoothScroller(this) {
            val MILLISECONDS_PER_INCH = 400f

            override fun calculateSpeedPerPixel(displayMetrics: DisplayMetrics?): Float {
                return MILLISECONDS_PER_INCH / displayMetrics!!.densityDpi
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupView()
        playSampleVideo()
        observeViewState()
    }

    private fun observeViewState() {
        viewModel.viewState.observe(
            this
        ) {
            when (it) {
                is State.Messages -> updateMessagesList(it.messages)
                is State.NewMessage -> updateMessagesList(adapter.currentList + it.message)
                is State.Error -> Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun setupView() {
        binding.messagesList.adapter = adapter
        binding.sendMessageButton.setOnClickListener {
            viewModel.sendButtonClicked(binding.messageInput.text.toString())
            binding.messageInput.setText("")
        }
    }

    private fun updateMessagesList(messages: List<Message>) {
        adapter.submitList(messages)
        messageListSmoothScroller.targetPosition = adapter.itemCount
        binding.messagesList.layoutManager?.startSmoothScroll(messageListSmoothScroller)
    }

    private fun playSampleVideo() {
        val playerListener = object : AbstractYouTubePlayerListener() {
            override fun onReady(youTubePlayer: YouTubePlayer) {
                youTubePlayer.loadVideo(videoId = "sJ0rDhmlMgM", startSeconds = 0f)
            }
        }
        val playerOptions = IFramePlayerOptions.Builder()
            .controls(0)
            .rel(0)
            .build()
        binding.mockLiveStreamView.initialize(playerListener, false, playerOptions)
    }
}
