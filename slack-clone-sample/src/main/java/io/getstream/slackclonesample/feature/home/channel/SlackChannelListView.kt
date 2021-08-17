package io.getstream.slackclonesample.feature.home.channel

import android.content.Context
import android.util.AttributeSet
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import io.getstream.chat.android.ui.channel.list.ChannelListView

class SlackChannelListView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0,
) : RecyclerView(context, attrs, defStyle) {

    private lateinit var adapter: SlackChannelListAdapter
    private lateinit var channelClickListener: ChannelListView.ChannelClickListener

    init {
        setHasFixedSize(true)
        layoutManager = LinearLayoutManager(context)
    }

    fun setChannelClickListener(listener: ChannelListView.ChannelClickListener) {
        this.channelClickListener = listener
    }

    fun setChannels(channels: List<SlackChannelListItem>) {
        requireAdapter().submitList(channels)
    }

    private fun requireAdapter(): SlackChannelListAdapter {
        if (::adapter.isInitialized.not()) {
            adapter = SlackChannelListAdapter(channelClickListener)

            this.setAdapter(adapter)

            adapter.registerAdapterDataObserver(SnapToTopDataObserver(this))
        }
        return adapter
    }

}