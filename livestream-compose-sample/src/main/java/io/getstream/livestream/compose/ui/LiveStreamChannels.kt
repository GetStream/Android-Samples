package io.getstream.livestream.compose.ui

import android.content.Context
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ViewStream
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import io.getstream.chat.android.client.models.name
import io.getstream.chat.android.compose.ui.theme.ChatTheme
import io.getstream.chat.android.compose.viewmodel.channel.ChannelListViewModel
import io.getstream.livestream.compose.R
import io.getstream.livestream.compose.models.LiveStreamChannelItem
import io.getstream.livestream.compose.models.LiveStreamType
import io.getstream.livestream.compose.models.LiveStreamType.*
import io.getstream.livestream.compose.randomArtWork
import io.getstream.livestream.compose.randomDescription

/**
 * View component to add custom live stream channels screen
 *
// * @param context - Local context for fetching resources to draw Icons or vectors
 * @param channelListViewModel - Stream channel list ViewModel to bind all channel data to custom UI provided here.
 * @param modifier - Modifier for styling.
 * @param isDarkTheme - a Boolean state to get Theme is dark or light , used for deciding card background
 * @param columnCount - a toggle State to switch between 2 column grid to single column list
 */
@ExperimentalMaterialApi
@ExperimentalFoundationApi
@Composable
fun LiveStreamChannels(
    channelListViewModel: ChannelListViewModel,
    modifier: Modifier = Modifier,
    isDarkTheme: Boolean = false,
    columnCount: Int = 2
) {
    LaunchedEffect(Unit) {
        channelListViewModel.start()
    }
    val cardBackground = if (isDarkTheme)
        colorResource(id = R.color.cardview_dark_background)
    else
        colorResource(id = R.color.cardview_light_background)
    val context: Context = LocalContext.current

    val channels = channelListViewModel.channelsState.channels.map { channel ->
        LiveStreamChannelItem(
            channelId = channel.cid,
            channelArt = context.randomArtWork(),
            channelName = channel.name,
            channelDescription = context.randomDescription()
        )
    }

    LazyVerticalGrid(
        modifier = modifier,
        cells = GridCells.Fixed(columnCount),
        contentPadding = PaddingValues(bottom = 12.dp, top = 12.dp),
        content = {
            items(channelListViewModel.channelsState.channels.size) { index ->
                Card(
                    shape = RoundedCornerShape(8.dp),
                    backgroundColor = cardBackground,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp)
                        .clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = rememberRipple(bounded = true),
                            onClick = {
                                openMessages(context, channels[index])
                            }
                        ),
                    elevation = 4.dp,
                ) {
                    Column(
                        modifier = Modifier
                            .height(240.dp)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(105.dp)
                        ) {
                            Image(
                                painterResource(channels[index].channelArt),
                                contentDescription = stringResource(id = R.string.accessibilityArtworkImage),
                                contentScale = ContentScale.Crop,
                            )
                        }
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(start = 8.dp, end = 8.dp)
                                .wrapContentHeight(),
                            verticalAlignment = Alignment.Top
                        ) {
                            Icon(
                                imageVector = Icons.Rounded.ViewStream,
                                contentDescription = stringResource(id = R.string.accessibilityIcon)
                            )
                            Text(
                                modifier = Modifier
                                    .align(Alignment.CenterVertically)
                                    .padding(start = 4.dp),
                                color = ChatTheme.colors.textHighEmphasis,
                                text = channels[index].channelName
                            )
                        }
                        Text(
                            modifier = Modifier
                                .padding(8.dp),
                            text = context.resources.getString(channels[index].channelDescription),
                            maxLines = 3,
                            color = ChatTheme.colors.textLowEmphasis,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                }
            }
        }
    )
}

private fun openMessages(context: Context, channel: LiveStreamChannelItem) {
    val liveStreamType = when {
        channel.channelName.contains("Youtube") -> {
            Youtube
        }
        channel.channelName.contains("Video") -> {
            Video
        }
        else -> Video
    }
    context.startActivity(LiveStreamActivity.getIntent(context, channel.channelId, liveStreamType))
}
