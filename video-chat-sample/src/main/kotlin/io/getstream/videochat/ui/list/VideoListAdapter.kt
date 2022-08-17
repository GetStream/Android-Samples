package io.getstream.videochat.ui.list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import coil.load
import io.getstream.videochat.R
import io.getstream.videochat.Video

class VideoListAdapter(
    private val onVideoSelected: (Video) -> Unit
) : RecyclerView.Adapter<VideoListAdapter.VideoViewHolder>() {

    private var videos: List<Video> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideoViewHolder {
        return LayoutInflater.from(parent.context)
            .inflate(R.layout.item_video, parent, false)
            .let { VideoViewHolder(it, onVideoSelected) }
    }

    override fun getItemCount(): Int = videos.size

    override fun onBindViewHolder(holder: VideoViewHolder, position: Int) {
        holder.bind(videos[position])
    }

    fun addVideos(newVideos: List<Video>) {
        videos = videos + newVideos
        notifyItemRangeInserted(videos.size - newVideos.size, newVideos.size)
    }

    class VideoViewHolder(
        itemView: View,
        private val onVideoSelected: (Video) -> Unit
    ) : RecyclerView.ViewHolder(itemView) {

        private val videoImageView: ImageView by lazy { itemView.findViewById(R.id.videoImageView) }
        private val videoNameTextView: TextView by lazy { itemView.findViewById(R.id.videoNameTextView) }

        fun bind(video: Video) {
            videoNameTextView.text = video.name
            videoImageView.load(video.image)
            itemView.setOnClickListener { onVideoSelected(video) }
        }
    }
}
