package io.getstream.videochat.ui.list

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import io.getstream.videochat.R
import io.getstream.videochat.SampleData
import io.getstream.videochat.ui.detail.VideoDetailActivity

class VideoListActivity : AppCompatActivity(R.layout.activity_video_list) {

    private val videoListAdapter = VideoListAdapter { video ->
        startActivity(VideoDetailActivity.createIntent(this, video))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        findViewById<RecyclerView>(R.id.videosRecyclerView).apply {
            adapter = videoListAdapter
            addItemDecoration(DividerItemDecoration(context, LinearLayoutManager.VERTICAL))
        }
        videoListAdapter.setVideos(SampleData.createVideos())
    }
}
