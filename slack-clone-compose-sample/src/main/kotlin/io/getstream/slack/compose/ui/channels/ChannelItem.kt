package io.getstream.slack.compose.ui.channels

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.getstream.chat.android.client.models.Channel
import io.getstream.chat.android.compose.ui.common.avatar.UserAvatar
import io.getstream.chat.android.compose.ui.theme.ChatTheme
import io.getstream.chat.android.compose.ui.util.getDisplayName
import io.getstream.slack.compose.R
import io.getstream.slack.compose.ui.channels.components.OnlineIndicator
import io.getstream.slack.compose.ui.channels.components.UnreadCountBadge
import io.getstream.slack.compose.ui.util.getOtherUser

/**
 * Component that represents a one-to-one channel item.
 *
 * One-to-one channel is a distinct channel with only two members.
 *
 * @param channel The channel to display.
 * @param onChannelClick Handler for a single tap on an item.
 * @param modifier Modifier for styling.
 */
@Composable
fun DirectOneToOneChatItem(
    channel: Channel,
    onChannelClick: (Channel) -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .clip(shape = RoundedCornerShape(6.dp))
            .clickable(onClick = { onChannelClick(channel) })
            .padding(horizontal = 8.dp)
            .fillMaxSize(),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        val user = channel.getOtherUser()!!

        Box(
            modifier = Modifier
                .clip(ChatTheme.shapes.avatar)
                .height(32.dp)
                .width(30.dp),
        ) {
            UserAvatar(
                modifier = Modifier
                    .width(24.dp)
                    .height(24.dp)
                    .align(Alignment.CenterStart),
                user = user
            )
            OnlineIndicator(
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .size(14.dp),
                online = user.online
            )
        }

        Spacer(Modifier.width(6.dp))
        ChannelName(
            channel = channel,
            modifier = Modifier.weight(1f),
        )

        val unreadCount = channel.unreadCount ?: 0
        if (unreadCount > 0) {
            UnreadCountBadge(unreadCount)
        }
    }
}

/**
 * Component that represents a distinct channel item.
 *
 * A distinct channel is a channel created without ID based on members.
 *
 * @param channel The channel to display.
 * @param onChannelClick Handler for a single tap on an item.
 * @param modifier Modifier for styling.
 */
@Composable
fun DirectGroupChatItem(
    channel: Channel,
    onChannelClick: (Channel) -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .clip(shape = RoundedCornerShape(6.dp))
            .clickable(onClick = { onChannelClick(channel) })
            .padding(horizontal = 8.dp)
            .fillMaxSize(),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Box(
            modifier = modifier
                .clip(RoundedCornerShape(4.dp))
                .size(24.dp)
                .background(ChatTheme.colors.borders),
        ) {
            Text(
                modifier = Modifier.align(Alignment.Center),
                text = "" + (channel.memberCount - 1),
                style = ChatTheme.typography.captionBold,
                fontSize = 12.sp,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                color = ChatTheme.colors.textHighEmphasis,
            )
        }
        Spacer(Modifier.width(12.dp))
        ChannelName(channel)
    }
}


/**
 * Component that represents a regular named channel.
 *
 * @param channel The channel to display.
 * @param onChannelClick Handler for a single tap on an item.
 * @param modifier Modifier for styling.
 * */
@Composable
fun ChannelItem(
    channel: Channel,
    onChannelClick: (Channel) -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .clip(shape = RoundedCornerShape(6.dp))
            .clickable(onClick = { onChannelClick(channel) })
            .padding(horizontal = 8.dp)
            .fillMaxSize(),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Spacer(Modifier.width(2.dp))
        Icon(
            modifier = Modifier.size(16.dp),
            painter = painterResource(id = R.drawable.ic_channel),
            contentDescription = null,
            tint = if (channel.hasUnread) {
                ChatTheme.colors.textHighEmphasis
            } else {
                ChatTheme.colors.textLowEmphasis
            },
        )
        Spacer(Modifier.width(18.dp))
        ChannelName(channel)
    }
}

/**
 * Component that represents a channel name.
 *
 * @param channel The channel used to display the name.
 * @param modifier Modifier for styling.
 */
@Composable
fun ChannelName(
    channel: Channel,
    modifier: Modifier = Modifier
) {
    Text(
        modifier = modifier
            .wrapContentWidth(align = Alignment.Start),
        text = channel.getDisplayName(),
        style = ChatTheme.typography.body,
        maxLines = 1,
        overflow = TextOverflow.Ellipsis,
        color = if (channel.hasUnread) ChatTheme.colors.textHighEmphasis else ChatTheme.colors.textLowEmphasis,
        fontWeight = if (channel.hasUnread) FontWeight.Bold else FontWeight.Normal
    )
}