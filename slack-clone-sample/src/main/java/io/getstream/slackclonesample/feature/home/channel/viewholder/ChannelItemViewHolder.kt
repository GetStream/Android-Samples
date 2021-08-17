package io.getstream.slackclonesample.feature.home.channel.viewholder

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import io.getstream.chat.android.client.models.Channel
import io.getstream.chat.android.ui.channel.list.ChannelListView
import io.getstream.chat.android.ui.common.extensions.getDisplayName
import io.getstream.slackclonesample.databinding.CustomChannelListItemBinding

class ChannelItemViewHolder(
    parent: ViewGroup,
    private val channelClickListener: ChannelListView.ChannelClickListener,
    private val binding: CustomChannelListItemBinding = CustomChannelListItemBinding.inflate(
        LayoutInflater.from(parent.context),
        parent,
        false
    )
) : RecyclerView.ViewHolder(binding.root) {

    private lateinit var channel: Channel

    init {
        binding.root.setOnClickListener { channelClickListener.onClick(channel) }
    }

     fun bind(channel: Channel) {
        this.channel = channel

        binding.apply {
            nameTextView.text = "# ${channel.getDisplayName(itemView.context)}"
            channel.unreadCount?.let {
                Log.d("ChannelItemViewHolder", "${channel.getDisplayName(itemView.context)} $it")
                if(it > 0) {
                    unreadCountBadge.text = "$it"
                    unreadCountBadge.visibility = View.VISIBLE
                } else unreadCountBadge.visibility = View.GONE
            }
        }
    }
}