package io.getstream.videochat

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import coil.api.load
import io.getstream.chat.android.client.ChatClient
import io.getstream.chat.android.client.errors.ChatError
import io.getstream.chat.android.client.logger.ChatLogLevel
import io.getstream.chat.android.client.socket.InitConnectionListener

private const val APIKEY = "sfgpnf7xhf2r"
class VideosListActivity : AppCompatActivity(R.layout.activity_videos_list) {

    private val videosAdapter = VideoAdapter(::onVideoSelected)

    private lateinit var client: ChatClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        findViewById<RecyclerView>(R.id.rvVideos).apply {
            adapter = videosAdapter
            addItemDecoration(VerticalDividerItemDecorator(context))
        }
        val progressBar: ProgressBar = findViewById(R.id.progressBar)

        val user = demoUsers.random()
        client = ChatClient.Builder(APIKEY, this.applicationContext)
            .logLevel(ChatLogLevel.ALL)
            .build()

        client.setUser(user, user.token, object : InitConnectionListener() {
            override fun onSuccess(data: ConnectionData) {
                Log.i("VideoListActivity", "setUser completed")
                videosAdapter.addVideos(videos)
                progressBar.visibility = View.GONE
            }

            override fun onError(error: ChatError) {
                Toast.makeText(applicationContext, error.toString(), Toast.LENGTH_LONG).show()
                Log.e("VideoListActivity", "setUser onError", error.cause)
            }
        })

    }

    private fun onVideoSelected(video: Video) {
        startActivity(createVideoIntent(this, video))
    }
}

class VideoAdapter(private val onVideoSelected: (Video) -> Unit) : RecyclerView.Adapter<VideoViewHolder>() {
    private var videos: List<Video> = listOf()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideoViewHolder = VideoViewHolder(parent, onVideoSelected)
    override fun getItemCount(): Int = videos.size
    override fun onBindViewHolder(holder: VideoViewHolder, position: Int) = holder.bind(videos[position])

    fun addVideos(newVideos: List<Video>) {
        videos = videos + newVideos
        notifyItemRangeInserted(videos.size - newVideos.size, newVideos.size)
    }
}

class VerticalDividerItemDecorator(context: Context) : DividerItemDecoration(context, VERTICAL) {
    init {
        ContextCompat.getDrawable(context, R.drawable.vertical_divider)?.let(::setDrawable)
    }
}


class VideoViewHolder(
    parent: ViewGroup,
    private val onVideoSelected: (Video) -> Unit
) : RecyclerView.ViewHolder(
    LayoutInflater.from(parent.context).inflate(R.layout.item_video, parent, false)
) {
    private val ivVideo: ImageView by lazy { itemView.findViewById<ImageView>(R.id.ivVideo) }
    private val tvVideoName: TextView by lazy { itemView.findViewById<TextView>(R.id.tvVideoName) }

    fun bind(video: Video) {
        tvVideoName.text = video.name
        ivVideo.load(video.image)
        itemView.setOnClickListener { onVideoSelected(video) }
    }
}
