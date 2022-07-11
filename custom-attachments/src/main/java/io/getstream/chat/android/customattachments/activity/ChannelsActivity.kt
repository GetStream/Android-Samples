package io.getstream.chat.android.customattachments.activity

import io.getstream.chat.android.client.models.Channel
import io.getstream.chat.android.ui.channel.ChannelListActivity
import io.getstream.chat.android.ui.channel.ChannelListFragment

class ChannelsActivity : ChannelListActivity(), ChannelListFragment.ChannelListItemClickListener {

    override fun onChannelClick(channel: Channel) {
        startActivity(MessagesActivity.createIntent(this, channel.cid))
    }
}
