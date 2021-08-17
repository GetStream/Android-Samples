package io.getstream.slackclonesample.feature.home.channel

import io.getstream.chat.android.client.models.Channel

sealed class SlackChannelListItem {
    public data class ChannelItem(val channel: Channel) : SlackChannelListItem()
    public object LoadingMoreItem : SlackChannelListItem()
    public data class SectionHeader(val sectionTitle: String): SlackChannelListItem()
}