package io.getstream.slackclonesample.feature.home.channel.viewholder

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import io.getstream.slackclonesample.databinding.ItemChannelSearchHeaderBinding

class SearchHeaderViewHolder(
    parent: ViewGroup,
    private val binding: ItemChannelSearchHeaderBinding = ItemChannelSearchHeaderBinding.inflate(
        LayoutInflater.from(parent.context),
        parent,
        false
    )
) : RecyclerView.ViewHolder(binding.root)