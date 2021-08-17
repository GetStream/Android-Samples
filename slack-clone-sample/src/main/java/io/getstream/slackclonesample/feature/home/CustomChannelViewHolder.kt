package io.getstream.slackclonesample.feature.home

import android.view.LayoutInflater
import android.view.ViewGroup
import io.getstream.chat.android.client.models.Channel
import io.getstream.chat.android.ui.channel.list.ChannelListView
import io.getstream.chat.android.ui.channel.list.adapter.ChannelListItem
import io.getstream.chat.android.ui.channel.list.adapter.ChannelListPayloadDiff
import io.getstream.chat.android.ui.channel.list.adapter.viewholder.BaseChannelListItemViewHolder
import io.getstream.chat.android.ui.channel.list.adapter.viewholder.ChannelListItemViewHolderFactory
import io.getstream.chat.android.ui.common.extensions.getDisplayName
import io.getstream.slackclonesample.databinding.CustomChannelListItemBinding

class CustomChannelListItemViewHolderFactory : ChannelListItemViewHolderFactory() {

    override fun getItemViewType(item: ChannelListItem): Int {
        return super.getItemViewType(item)
    }

    override fun createViewHolder(
        parentView: ViewGroup,
        viewType: Int
    ): BaseChannelListItemViewHolder {
        return super.createViewHolder(parentView, viewType)
    }

    override fun createChannelViewHolder(parentView: ViewGroup): BaseChannelListItemViewHolder {
        return CustomChannelListItemViewHolder(parentView, listenerContainer.channelClickListener)
    }
}

class CustomChannelListItemViewHolder(
    parent: ViewGroup,
    private val channelClickListener: ChannelListView.ChannelClickListener,
    private val binding: CustomChannelListItemBinding = CustomChannelListItemBinding.inflate(
        LayoutInflater.from(parent.context),
        parent,
        false
    ),
) : BaseChannelListItemViewHolder(binding.root) {

    private lateinit var channel: Channel

    init {
        binding.root.setOnClickListener { channelClickListener.onClick(channel) }
    }

    override fun bind(channel: Channel, diff: ChannelListPayloadDiff) {
        this.channel = channel

        binding.apply {
            nameTextView.text = "# ${channel.getDisplayName(itemView.context)}"
        }
    }
}
