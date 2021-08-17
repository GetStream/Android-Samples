package io.getstream.slackclonesample.feature.home.channel.viewholder

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import io.getstream.slackclonesample.databinding.ItemChannelSectionHeaderBinding
import io.getstream.slackclonesample.feature.home.channel.SlackChannelListItem

class ChannelSectionViewHolder(
    parent: ViewGroup,
    private val binding: ItemChannelSectionHeaderBinding = ItemChannelSectionHeaderBinding.inflate(
        LayoutInflater.from(parent.context),
        parent,
        false
    )
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(header: SlackChannelListItem.SectionHeader) {

        binding.apply {
            sectionTitle.text = header.sectionTitle
        }
    }
}