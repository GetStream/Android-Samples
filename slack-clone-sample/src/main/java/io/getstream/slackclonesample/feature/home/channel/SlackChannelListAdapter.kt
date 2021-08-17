package io.getstream.slackclonesample.feature.home.channel

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import io.getstream.slackclonesample.feature.home.channel.viewholder.ChannelItemViewHolder
import io.getstream.slackclonesample.feature.home.channel.viewholder.ChannelSectionViewHolder
import io.getstream.slackclonesample.feature.home.channel.viewholder.SearchHeaderViewHolder
import io.getstream.chat.android.ui.channel.list.ChannelListView

class SlackChannelListAdapter(private val channelClickListener: ChannelListView.ChannelClickListener) :
    ListAdapter<SlackChannelListItem, RecyclerView.ViewHolder>(SlackChannelListItemDiffCallback) {

    override fun getItemViewType(position: Int): Int {
        return when {
            position == 0 -> {
                SEARCH_HEADER
            }
            getItem(position - 1) is SlackChannelListItem.SectionHeader -> {
                CHANNEL_SECTION_HEADER
            }
            else -> CHANNEL_ITEM
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            SEARCH_HEADER -> SearchHeaderViewHolder(parent)
            CHANNEL_SECTION_HEADER -> ChannelSectionViewHolder(parent)
            CHANNEL_ITEM -> ChannelItemViewHolder(parent, channelClickListener)
            else -> throw IllegalArgumentException("Unhandled ChannelList view type: $viewType")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is ChannelItemViewHolder -> {
                holder.bind((getItem(position - 1) as SlackChannelListItem.ChannelItem).channel)
            }
            is ChannelSectionViewHolder -> {
                val sectionHeader = getItem(position - 1) as SlackChannelListItem.SectionHeader
                holder.bind(sectionHeader)
            }
        }
    }

    override fun getItemCount(): Int {
        return super.getItemCount() + 1 // Adding 1 here for search header
    }

    companion object {
        const val SEARCH_HEADER = 756
        const val CHANNEL_SECTION_HEADER = 757
        const val CHANNEL_ITEM = 758
    }
}