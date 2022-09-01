/*
 * Copyright 2022 Stream.IO, Inc. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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
    private val onVideoSelected: (YoutubeVideo) -> Unit
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
        private val onVideoSelected: (YoutubeVideo) -> Unit
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
