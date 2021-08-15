package io.getstream.livestream.compose.ui

import android.content.Context
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import io.getstream.chat.android.client.ChatClient
import io.getstream.chat.android.client.api.models.QuerySort
import io.getstream.chat.android.client.models.Channel
import io.getstream.chat.android.client.models.Filters
import io.getstream.chat.android.client.models.name
import io.getstream.chat.android.compose.ui.channel.list.ChannelList
import io.getstream.chat.android.compose.ui.theme.ChatTheme
import io.getstream.chat.android.compose.viewmodel.channel.ChannelListViewModel
import io.getstream.chat.android.compose.viewmodel.channel.ChannelViewModelFactory
import io.getstream.chat.android.offline.ChatDomain
import io.getstream.livestream.compose.models.LiveStreamChannelItem
import io.getstream.livestream.compose.models.LiveStreamType
import io.getstream.livestream.compose.randomArtWork
import io.getstream.livestream.compose.randomDescription

/**
 * View component to add custom live stream channels screen.
 *
 * @param modifier - Modifier for styling.
 * @param channelListViewModel - Stream channel list ViewModel to bind all channel data to custom UI provided here.
 * @param isDarkTheme - a Boolean state to get Theme is dark or light , used for deciding card background
 * @param isGrid - a toggle State to switch between 2 column grid to single column list
 * @param gridColumn - customization for Grid view column count.
 */
@ExperimentalMaterialApi
@ExperimentalFoundationApi
@Composable
fun LiveStreamChannels(
    modifier: Modifier = Modifier,
    channelListViewModel: ChannelListViewModel = viewModel(
        factory =
        ChannelViewModelFactory(
            ChatClient.instance(),
            ChatDomain.instance(),
            QuerySort.desc("last_updated"),
            Filters.and(
                Filters.eq("type", "livestream"),
                Filters.`in`("members", listOf(ChatClient.instance().getCurrentUser()?.id ?: ""))
            )
        )
    ),
    isDarkTheme: Boolean = false,
    isGrid: Boolean = false,
    gridColumn: Int = 2
) {
    val context: Context = LocalContext.current

    // We load a [LazyVerticalGrid] when we have a grid view to show
    if (isGrid) {
        // We fetch channels first
        val channels = channelListViewModel.channelsState.channels.map { channel ->
            channel.toLiveStreamChannelItem(context = context)
        }

        LazyVerticalGrid(
            modifier = modifier.background(ChatTheme.colors.appBackground),
            cells = GridCells.Fixed(gridColumn),
            contentPadding = PaddingValues(bottom = 12.dp, top = 12.dp),
            content = {
                items(channels) { item ->
                    LiveStreamChannelCard(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(10.dp),
                        isDarkTheme = isDarkTheme,
                        liveStreamChannelItem = item,
                        onCardClick = { clickedItem ->
                            openLiveStream(context, clickedItem)
                        }
                    )
                }
            }
        )
    } else {
        // We load a [LazyColumn] for when list is required
        ChannelList(
            modifier = modifier.background(ChatTheme.colors.appBackground),
            viewModel = channelListViewModel
        ) { channel ->
            LiveStreamChannelCard(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp),
                isDarkTheme = isDarkTheme,
                liveStreamChannelItem = channel.toLiveStreamChannelItem(
                    context = context,
                ),
                onCardClick = { clickedItem ->
                    openLiveStream(context, clickedItem)
                }
            )
        }
    }
}

private fun Channel.toLiveStreamChannelItem(
    context: Context
): LiveStreamChannelItem {
    return LiveStreamChannelItem(
        channelId = cid,
        channelArt = context.randomArtWork(),
        channelName = name,
        channelDescription = context.randomDescription()
    )
}

/**
 * This function provides a way to open the [LiveStreamActivity] by passing channel item and also type of livestream to load
 *
 * @param context - Context is required to start live stream activity
 * @param channel - the clicked [LiveStreamChannelItem] item to pass as a parcelable
 */
private fun openLiveStream(context: Context, channel: LiveStreamChannelItem) {
    // Dummy logic to decide the type of live stream screen to show
    val liveStreamType = when {
        channel.channelName.contains("Youtube") -> {
            LiveStreamType.Youtube
        }
        channel.channelName.contains("Video") -> {
            LiveStreamType.Video
        }
        else -> LiveStreamType.Camera
    }
    context.startActivity(LiveStreamActivity.getIntent(context, channel.channelId, liveStreamType))
}
