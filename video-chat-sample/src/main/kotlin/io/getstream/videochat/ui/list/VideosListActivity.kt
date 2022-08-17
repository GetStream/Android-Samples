package io.getstream.videochat.ui.list

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import io.getstream.videochat.R
import io.getstream.videochat.Video
import io.getstream.videochat.ui.detail.VideoActivity
import io.getstream.videochat.videos

class VideosListActivity : AppCompatActivity(R.layout.activity_videos_list) {

    private val videosAdapter = VideoListAdapter(::onVideoSelected)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        findViewById<RecyclerView>(R.id.videosRecyclerView).apply {
            adapter = videosAdapter
            addItemDecoration(VerticalDividerItemDecorator(context))
        }

        videosAdapter.addVideos(videos)
    }

    private fun onVideoSelected(video: Video) {
        startActivity(VideoActivity.createIntent(this, video))
    }

    class VerticalDividerItemDecorator(
        context: Context
    ) : DividerItemDecoration(context, VERTICAL) {
        init {
            ContextCompat.getDrawable(context, R.drawable.vertical_divider)?.let(::setDrawable)
        }
    }
}
