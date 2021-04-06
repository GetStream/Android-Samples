package io.getstream.videochat.video_list

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import io.getstream.chat.android.client.ChatClient
import io.getstream.chat.android.client.logger.ChatLogLevel
import io.getstream.videochat.R
import io.getstream.videochat.Video
import io.getstream.videochat.demoUsers
import io.getstream.videochat.token
import io.getstream.videochat.video.createVideoIntent
import io.getstream.videochat.videos

class VideosListActivity : AppCompatActivity(R.layout.activity_videos_list) {

    private val videosAdapter = VideoListAdapter(::onVideoSelected)

    private lateinit var client: ChatClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        findViewById<RecyclerView>(R.id.videoList).apply {
            adapter = videosAdapter
            addItemDecoration(VerticalDividerItemDecorator(context))
        }
        val progressBar: ProgressBar = findViewById(R.id.progressBar)

        val user = demoUsers.random()
        client = ChatClient.Builder(APIKEY, this.applicationContext)
            .logLevel(ChatLogLevel.ALL)
            .build()

        client.connectUser(user, user.token).enqueue {
            if (it.isSuccess) {
                Log.i("VideoListActivity", "setUser completed")
                videosAdapter.addVideos(videos)
                progressBar.visibility = View.GONE
            } else {
                Toast.makeText(applicationContext, it.error().message.toString(), Toast.LENGTH_LONG)
                    .show()
                Log.e("VideoListActivity", "setUser onError", it.error().cause)
            }
        }
    }

    private fun onVideoSelected(video: Video) {
        startActivity(createVideoIntent(this, video))
    }

    class VerticalDividerItemDecorator(
        context: Context
    ) : DividerItemDecoration(context, VERTICAL) {
        init {
            ContextCompat.getDrawable(context, R.drawable.vertical_divider)?.let(::setDrawable)
        }
    }

    companion object {
        private const val APIKEY = "sfgpnf7xhf2r"
    }
}
