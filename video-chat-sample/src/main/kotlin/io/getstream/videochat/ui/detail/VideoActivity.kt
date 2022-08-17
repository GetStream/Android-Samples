package io.getstream.videochat.ui.detail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.RecyclerView
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.YouTubePlayerCallback
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView
import io.getstream.chat.android.client.ChatClient
import io.getstream.chat.android.client.api.models.QueryChannelRequest
import io.getstream.chat.android.client.channel.ChannelClient
import io.getstream.chat.android.client.events.NewMessageEvent
import io.getstream.chat.android.client.models.Message
import io.getstream.chat.android.client.utils.observable.Disposable
import io.getstream.videochat.R
import io.getstream.videochat.Video
import io.getstream.videochat.nicknameColor

private const val CHANNEL_TYPE = "livestream"

class VideoActivity : AppCompatActivity(R.layout.activity_video) {

    private val messageListAdapter: MessageListAdapter = MessageListAdapter()
    private lateinit var input: EditText
    private lateinit var sendMessageButton: ImageView
    private lateinit var rvMessages: RecyclerView
    private lateinit var channelClient: ChannelClient
    private var subscription: Disposable? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val video = intent.getSerializableExtra(EXTRA_VIDEO) as Video
        val chatClient = ChatClient.instance()
        findViewById<YouTubePlayerView>(R.id.videoPlayer).apply {
            lifecycle.addObserver(this)
            getYouTubePlayerWhenReady(object : YouTubePlayerCallback {
                override fun onYouTubePlayer(youTubePlayer: YouTubePlayer) {
                    youTubePlayer.loadVideo(video.id, 0f)
                }
            })
        }
        rvMessages =
            findViewById<RecyclerView>(R.id.messageList).apply { adapter = messageListAdapter }
        sendMessageButton = findViewById<ImageView>(R.id.sendButton).apply {
            visibility = View.GONE
            setOnClickListener { sendMessage() }
        }
        input = findViewById<EditText>(R.id.input).apply {
            this.addTextChangedListener {
                sendMessageButton.visibility = if (it.isNullOrBlank()) View.GONE else View.VISIBLE
            }
            chatClient.getCurrentUser()?.getExtraValue("name", "")
                ?.takeIf { it.isNotBlank() }
                ?.let { userName -> hint = "Chat publicly as $userName..." }
            isEnabled = false
        }
        channelClient = chatClient.channel(CHANNEL_TYPE, video.id)
        channelClient.query(
            QueryChannelRequest()
                .withData(mapOf("name" to "Live Chat for video '${video.name}'"))
                .withMessages(100)
                .withWatch()
        ).enqueue {
            when (it.isSuccess) {
                true -> {
                    addMessages(it.data().messages)
                    input.isEnabled = true
                }
                false -> {
                    Toast.makeText(applicationContext, it.error().toString(), Toast.LENGTH_LONG)
                        .show()
                    Log.e("VideoActivity", "Create Channel error", it.error().cause)
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        subscription = channelClient.subscribe { chatEvent ->
            chatEvent
                .takeIf { it.type == "message.new" }
                ?.let { addMessage((it as NewMessageEvent).message) }
        }
    }

    private fun addMessage(message: Message) = addMessages(listOf(message))

    private fun addMessages(messages: List<Message>) {
        messageListAdapter.addMessages(messages.map(Message::toViewHolderMessage))
        rvMessages.post { rvMessages.smoothScrollToPosition(messageListAdapter.itemCount) }
    }

    override fun onPause() {
        super.onPause()
        channelClient.stopWatching().enqueue()
        subscription?.dispose()
    }

    private fun sendMessage() {
        input.text
            ?.toString()
            ?.takeIf { it.isNotBlank() }
            ?.let {
                input.setText("")
                channelClient.sendMessage(Message().apply { text = it }).enqueue()
            }
    }

    companion object {
        private const val EXTRA_VIDEO = "extra_video"

        fun createIntent(context: Context, video: Video): Intent {
            return Intent(context, VideoActivity::class.java).apply {
                putExtra(EXTRA_VIDEO, video)
            }
        }
    }
}

fun Message.toViewHolderMessage() =
    MessageListAdapter.MessageViewHolder.Message(
        id,
        createdAt?.time ?: 0,
        user.getExtraValue("image", ""),
        user.getExtraValue("name", ""),
        user.nicknameColor,
        text
    )
