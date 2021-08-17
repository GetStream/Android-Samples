@file:JvmName("SlackChannelListViewModelBinding")
package io.getstream.slackclonesample.feature.home.channel

import androidx.lifecycle.LifecycleOwner
import io.getstream.chat.android.client.models.Channel
import io.getstream.chat.android.ui.channel.list.ChannelListView
import io.getstream.chat.android.ui.channel.list.viewmodel.ChannelListViewModel

/**
 * Binds [ChannelListView] with [ChannelListViewModel], updating the view's state based on
 * data provided by the ViewModel, and propagating view events to the ViewModel as needed.
 *
 * This function sets listeners on the view and ViewModel. Call this method
 * before setting any additional listeners on these objects yourself.
 */
@JvmName("bind")
fun ChannelListViewModel.bindListView(
    view: SlackChannelListView,
    lifecycleOwner: LifecycleOwner,
) {
    state.observe(lifecycleOwner) { channelState ->
        if (!channelState.isLoading) {
            channelState
                .channels
                .transform()
                .let(view::setChannels)
        } else {
            // TODO: handle loading state here.
        }
    }
}

private fun List<Channel>.transform(): List<SlackChannelListItem> {
    val groupChannels = arrayListOf<SlackChannelListItem.ChannelItem>()
    val directChannels = arrayListOf<SlackChannelListItem.ChannelItem>()
    val resultantList = arrayListOf<SlackChannelListItem>()

    this.forEach {
        if(it.getUsersWithoutSelf().size == 1) {
            directChannels.add(SlackChannelListItem.ChannelItem(it))
        } else {
            groupChannels.add(SlackChannelListItem.ChannelItem(it))
        }
    }
    resultantList.add(SlackChannelListItem.SectionHeader("Channels"))
    resultantList.addAll(groupChannels)
    resultantList.add(SlackChannelListItem.SectionHeader("Direct Messages"))
    resultantList.addAll(directChannels)
    return resultantList
}
