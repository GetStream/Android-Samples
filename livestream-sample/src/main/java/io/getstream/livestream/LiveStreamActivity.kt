package io.getstream.livestream

import android.os.Bundle
import android.util.DisplayMetrics
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearSmoothScroller
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.options.IFramePlayerOptions
import io.getstream.chat.android.client.models.Message
import kotlinx.android.synthetic.main.activity_main.*

class LiveStreamActivity : AppCompatActivity(R.layout.activity_main) {
    private val adapter = MessagesListAdapter()

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

        loadMockVideoStream()
        messagesList.adapter = adapter

        val viewModel: LiveStreamViewModel by viewModels()
        viewModel.viewState.observe(this, Observer {
            when (it) {
                is State.Messages -> updateMessagesList(it.messages)
                is State.NewMessage -> updateMessagesList(adapter.currentList + it.message)
                is State.Error -> showToast(it.message)
            }
        })

        sendMessageButton.setOnClickListener {
            viewModel.sendButtonClicked(messageInput.text.toString())
            messageInput.setText("")
            messageInput.clearFocus()
            messageInput.hideKeyboard()
        }
    }

    private fun updateMessagesList(messages: List<Message>) {
        adapter.submitList(messages)
        adapter.notifyDataSetChanged()
        val scrollTarget = adapter.itemCount
        messageListSmoothScroller.targetPosition = scrollTarget
        messagesList.layoutManager?.startSmoothScroll(messageListSmoothScroller)
    }

    private fun loadMockVideoStream() {
        val playerListener = object : AbstractYouTubePlayerListener() {
            override fun onReady(youTubePlayer: YouTubePlayer) {
                youTubePlayer.loadVideo(videoId = "XYqrrpvTtU8", startSeconds = 0f)
            }
        }
        val playerOptions = IFramePlayerOptions.Builder().controls(0).rel(0).build()
        mockLiveStreamView.initialize(playerListener, false, playerOptions)
    }
}
