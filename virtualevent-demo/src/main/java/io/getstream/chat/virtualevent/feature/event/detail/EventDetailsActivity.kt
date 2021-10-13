package io.getstream.chat.virtualevent.feature.event.detail

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.getstream.sdk.chat.viewmodel.MessageInputViewModel
import com.getstream.sdk.chat.viewmodel.messages.MessageListViewModel
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.SimpleExoPlayer
import io.getstream.chat.android.ui.message.input.viewmodel.bindView
import io.getstream.chat.android.ui.message.list.viewmodel.bindView
import io.getstream.chat.android.ui.message.list.viewmodel.factory.MessageListViewModelFactory
import io.getstream.chat.virtualevent.databinding.ActivityEventDetailsBinding
import io.getstream.chat.virtualevent.util.setupToolbar

/**
 * Activity that shows a live video stream and a list of comments
 * associated with this stream.
 */
class EventDetailsActivity : AppCompatActivity() {

    private lateinit var messageListViewModel: MessageListViewModel
    private lateinit var messageInputViewModel: MessageInputViewModel
    private lateinit var binding: ActivityEventDetailsBinding

    private var player: SimpleExoPlayer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEventDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val cid = intent.getStringExtra(KEY_EXTRA_CID)!!
        val factory = MessageListViewModelFactory(cid = cid)
        messageListViewModel = factory.create(MessageListViewModel::class.java)
        messageInputViewModel = factory.create(MessageInputViewModel::class.java)

        setupToolbar()
        setupMessageListView()
        setupMessageInputView()
    }

    override fun onStart() {
        super.onStart()
        initializePlayer()
        binding.playerView?.onResume()
    }

    override fun onStop() {
        super.onStop()
        binding.playerView?.onPause()
        releasePlayer()
    }

    private fun setupToolbar() {
        setupToolbar(binding.toolbar)
        binding.titleTextView.text = intent.getStringExtra(KEY_EVENT_TITLE)!!
    }

    private fun setupMessageListView() {
        binding.messageListView.setMessageViewHolderFactory(LivestreamMessageItemVhFactory())
        binding.messageListView.setCustomLinearLayoutManager(
            LinearLayoutManager(this).apply {
                stackFromEnd = false
            }
        )
        messageListViewModel.apply {
            bindView(binding.messageListView, this@EventDetailsActivity)
        }
    }

    private fun setupMessageInputView() {
        messageInputViewModel.apply {
            bindView(binding.messageInputView, this@EventDetailsActivity)
        }
    }

    private fun initializePlayer() {
        player = SimpleExoPlayer.Builder(this)
            .build()
            .also { exoPlayer ->
                binding.playerView.player = exoPlayer

                val videoUri = Uri.parse("asset:///speaker.mp4")
                exoPlayer.setMediaItem(MediaItem.fromUri(videoUri))
                exoPlayer.repeatMode = Player.REPEAT_MODE_ONE
                exoPlayer.prepare()
                exoPlayer.play()
            }
    }

    private fun releasePlayer() {
        player?.release()
        player = null
    }

    companion object {
        private const val KEY_EXTRA_CID: String = "extra_cid"
        private const val KEY_EVENT_TITLE: String = "extra_title"

        fun createIntent(
            context: Context,
            cid: String,
            title: String
        ): Intent {
            return Intent(context, EventDetailsActivity::class.java)
                .putExtra(KEY_EXTRA_CID, cid)
                .putExtra(KEY_EVENT_TITLE, title)
        }
    }
}
