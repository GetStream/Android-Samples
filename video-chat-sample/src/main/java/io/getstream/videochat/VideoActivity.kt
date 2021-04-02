package io.getstream.videochat

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.ColorInt
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.api.load
import coil.transform.CircleCropTransformation
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.YouTubePlayerCallback
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView
import io.getstream.chat.android.client.ChatClient
import io.getstream.chat.android.client.api.models.QueryChannelRequest
import io.getstream.chat.android.client.channel.ChannelClient
import io.getstream.chat.android.client.events.NewMessageEvent
import io.getstream.chat.android.client.models.Message
import io.getstream.chat.android.client.utils.observable.Disposable
import kotlin.properties.Delegates

private const val VIDEO_INTENT_EXTRA = "VIDEO_INTENT_EXTRA"
private const val CHANNEL_TYPE = "livestream"

class VideoActivity : AppCompatActivity(R.layout.activity_video) {

    private val messageAdapter: MessageAdapter = MessageAdapter()
    private lateinit var input: EditText
    private lateinit var sendMessageButton: ImageView
    private lateinit var rvMessages: RecyclerView
    private lateinit var channelClient: ChannelClient
    private var subscription: Disposable? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val video = intent.getSerializableExtra(VIDEO_INTENT_EXTRA) as Video
        val chatClient = ChatClient.instance()
        findViewById<YouTubePlayerView>(R.id.videoPlayer).apply {
            lifecycle.addObserver(this)
            getYouTubePlayerWhenReady(object : YouTubePlayerCallback {
                override fun onYouTubePlayer(youTubePlayer: YouTubePlayer) {
                    youTubePlayer.loadVideo(video.id, 0f)
                }
            })
        }
        rvMessages = findViewById<RecyclerView>(R.id.rvMessages).apply { adapter = messageAdapter }
        sendMessageButton = findViewById<ImageView>(R.id.ivSend).apply {
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
        messageAdapter.addMessages(messages.map(Message::toViewHolderMessage))
        rvMessages.post { rvMessages.smoothScrollToPosition(messageAdapter.itemCount) }
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
}

class MessageAdapter : RecyclerView.Adapter<MessageViewHolder>() {
    private var messages: List<MessageViewHolder.Message> by Delegates.observable(listOf()) { _, _, newMessages ->
        asyncListDiffer.submitList(
            newMessages.takeIf { it.isNotEmpty() })
    }
    private val asyncListDiffer: AsyncListDiffer<MessageViewHolder.Message> by lazy {
        AsyncListDiffer(this, object : DiffUtil.ItemCallback<MessageViewHolder.Message>() {
            override fun areContentsTheSame(
                oldItem: MessageViewHolder.Message,
                newItem: MessageViewHolder.Message
            ): Boolean = oldItem == newItem

            override fun areItemsTheSame(
                oldItem: MessageViewHolder.Message,
                newItem: MessageViewHolder.Message
            ): Boolean = oldItem.id == newItem.id
        })
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageViewHolder =
        MessageViewHolder(parent)

    override fun getItemCount(): Int = asyncListDiffer.currentList.size
    override fun onBindViewHolder(holder: MessageViewHolder, position: Int) =
        holder.bind(asyncListDiffer.currentList[position])

    fun addMessages(newMessages: List<MessageViewHolder.Message>) {
        messages = (messages + newMessages).sortedBy { it.timestamp }
    }
}

class MessageViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
    LayoutInflater.from(parent.context).inflate(R.layout.item_message, parent, false)
) {
    private val ivAvatar: ImageView by lazy { itemView.findViewById<ImageView>(R.id.ivAvatar) }
    private val tvUserName: TextView by lazy { itemView.findViewById<TextView>(R.id.tvUserName) }
    private val tvMessage: TextView by lazy { itemView.findViewById<TextView>(R.id.tvMessage) }

    fun bind(message: Message) {
        tvUserName.apply {
            text = message.userName
            setTextColor(message.colorName)
        }
        tvMessage.text = message.text
        ivAvatar.load(message.avatarUrl) { transformations(CircleCropTransformation()) }
    }

    data class Message(
        val id: String,
        val timestamp: Long,
        val avatarUrl: String,
        val userName: String,
        @ColorInt val colorName: Int,
        val text: String
    )
}

fun Message.toViewHolderMessage() =
    MessageViewHolder.Message(
        id,
        createdAt?.time ?: 0,
        user.getExtraValue("image", ""),
        user.getExtraValue("name", ""),
        user.nicknameColor,
        text
    )

fun createVideoIntent(context: Context, video: Video) =
    Intent(context, VideoActivity::class.java).apply {
        putExtra(VIDEO_INTENT_EXTRA, video)
    }
