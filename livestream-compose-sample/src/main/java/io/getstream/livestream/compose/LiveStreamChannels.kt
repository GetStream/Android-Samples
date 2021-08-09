package io.getstream.livestream.compose

import android.content.Context
import android.content.res.TypedArray
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import io.getstream.chat.android.client.models.name
import io.getstream.chat.android.compose.ui.theme.ChatTheme
import io.getstream.chat.android.compose.viewmodel.channel.ChannelListViewModel
import java.util.*

@ExperimentalMaterialApi
@ExperimentalFoundationApi
@Composable
fun LiveStreamChannels(
    modifier: Modifier = Modifier,
    context: Context,
    channelListViewModel: ChannelListViewModel,
) {
    LaunchedEffect(Unit) {
        channelListViewModel.start()
    }

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
        cells = GridCells.Fixed(2),
        contentPadding = PaddingValues(bottom = 12.dp, top = 12.dp),
        content = {
            items(channelListViewModel.channelsState.channels.size) { index ->
                Card(
                    shape = RoundedCornerShape(8.dp),
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
                                contentDescription = "",
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
                                modifier = Modifier.align(Alignment.CenterVertically),
                                imageVector = Icons.Rounded.ViewStream,
                                contentDescription = null
                            )
                            Text(
                                modifier = Modifier
                                    .align(Alignment.CenterVertically)
                                    .padding(start = 4.dp),
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

fun Context.randomArtWork(): Int {
    val rand = Random()
    val artworksArray: TypedArray = resources.obtainTypedArray(R.array.artwork)
    val rndInt: Int = rand.nextInt(artworksArray.length())
    return artworksArray.getResourceId(rndInt, 0).also {
        artworksArray.recycle()
    }
}

fun Context.randomDescription(): Int {
    val rand = Random()
    val descArray: TypedArray = resources.obtainTypedArray(R.array.description)
    val rndInt: Int = rand.nextInt(descArray.length())
    return descArray.getResourceId(rndInt, 0).also {
        descArray.recycle()
    }
}

private fun openMessages(context: Context, channel: LiveStreamChannelItem) {
    val liveStreamType = when {
        channel.channelName.contains("Youtube") -> {
            LiveStreamType.Youtube
        }
        channel.channelName.contains("Video") -> {
            LiveStreamType.Video
        }
        else -> LiveStreamType.Video
    }
    context.startActivity(LiveStreamActivity.getIntent(context, channel.channelId, liveStreamType))
}

data class LiveStreamChannelItem(
    val channelId: String,
    val channelName: String,
    @StringRes
    val channelDescription: Int,
    @DrawableRes
    val channelArt: Int
)
