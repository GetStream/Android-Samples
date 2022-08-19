package io.getstream.videochat.ui.list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.RoundedCornersTransformation
import io.getstream.videochat.R
import io.getstream.videochat.YoutubeVideo

class VideoListAdapter(
    private val onVideoSelected: (YoutubeVideo) -> Unit,
) : RecyclerView.Adapter<VideoListAdapter.VideoViewHolder>() {

    private var videos: List<YoutubeVideo> = listOf()

    fun setVideos(videos: List<YoutubeVideo>) {
        this.videos = videos
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideoViewHolder {
        return LayoutInflater.from(parent.context)
            .inflate(R.layout.item_video, parent, false)
            .let { VideoViewHolder(it, onVideoSelected) }
    }

    override fun getItemCount(): Int = videos.size

    override fun onBindViewHolder(holder: VideoViewHolder, position: Int) {
        holder.bind(videos[position])
    }

    class VideoViewHolder(
        itemView: View,
        private val onVideoSelected: (YoutubeVideo) -> Unit,
    ) : RecyclerView.ViewHolder(itemView) {

        private lateinit var video: YoutubeVideo

        private val videoImageView: ImageView by lazy { itemView.findViewById(R.id.videoImageView) }
        private val descriptionTextView: TextView by lazy { itemView.findViewById(R.id.descriptionTextView) }

        init {
            itemView.setOnClickListener { onVideoSelected(video) }
        }

        fun bind(video: YoutubeVideo) {
            this.video = video

            descriptionTextView.text = video.name

            val corners = videoImageView.context.resources
                .getDimensionPixelSize(R.dimen.rounded_corners).toFloat()
            videoImageView.load(video.image) {
                transformations(RoundedCornersTransformation(corners))
            }
        }
    }
}
