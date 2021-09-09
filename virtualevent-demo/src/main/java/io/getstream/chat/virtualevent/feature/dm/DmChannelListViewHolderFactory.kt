package io.getstream.chat.virtualevent.feature.dm

import android.view.LayoutInflater
import android.view.ViewGroup
import io.getstream.chat.android.client.models.Channel
import io.getstream.chat.android.ui.channel.list.ChannelListView
import io.getstream.chat.android.ui.channel.list.adapter.ChannelListPayloadDiff
import io.getstream.chat.android.ui.channel.list.adapter.viewholder.BaseChannelListItemViewHolder
import io.getstream.chat.android.ui.channel.list.adapter.viewholder.ChannelListItemViewHolderFactory
import io.getstream.chat.android.ui.common.extensions.getDisplayName
import io.getstream.chat.android.ui.common.extensions.getLastMessage
import io.getstream.chat.virtualevent.databinding.ItemDmChannelListBinding

class DmChannelListViewHolderFactory : ChannelListItemViewHolderFactory() {

    override fun createChannelViewHolder(parentView: ViewGroup): BaseChannelListItemViewHolder {
        return DmChannelListItemViewHolder(parentView, listenerContainer.channelClickListener)
    }
}

class DmChannelListItemViewHolder(
    parent: ViewGroup,
    private val channelClickListener: ChannelListView.ChannelClickListener,
    private val binding: ItemDmChannelListBinding = ItemDmChannelListBinding.inflate(
        LayoutInflater.from(parent.context),
        parent,
        false
    ),
) : BaseChannelListItemViewHolder(binding.root) {

    private lateinit var channel: Channel

    init {
        binding.root.setOnClickListener {
            channelClickListener.onClick(channel)
        }
    }

    override fun bind(channel: Channel, diff: ChannelListPayloadDiff) {
        this.channel = channel

        binding.apply {
            avatarView.setChannelData(channel)
            nameTextView.text = channel.getDisplayName(itemView.context)
            membersCountTextView.text = channel.getLastMessage()?.text
        }
    }
}
