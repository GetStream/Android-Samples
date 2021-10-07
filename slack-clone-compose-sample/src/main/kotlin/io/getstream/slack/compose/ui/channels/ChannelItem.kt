package io.getstream.slack.compose.ui.channels

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.getstream.chat.android.client.models.Channel
import io.getstream.chat.android.compose.ui.common.avatar.UserAvatar
import io.getstream.chat.android.compose.ui.theme.ChatTheme
import io.getstream.chat.android.compose.ui.util.getDisplayName
import io.getstream.slack.compose.R
import io.getstream.slack.compose.ui.channels.components.OnlineIndicator
import io.getstream.slack.compose.ui.channels.components.UnreadCountBadge
import io.getstream.slack.compose.ui.theme.SlackTheme
import io.getstream.slack.compose.ui.util.getOtherUser

/**
 * Component that represents a group channel item.
 *
 * @param channel The channel to display.
 * @param onChannelClick Handler for a single tap on an item.
 * @param modifier Modifier for styling.
 * */
@Composable
fun GroupChannelItem(
    channel: Channel,
    onChannelClick: (Channel) -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .clickable { onChannelClick(channel) }
            .padding(vertical = 8.dp)
            .fillMaxWidth()
            .height(24.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Spacer(Modifier.width(16.dp))

        Icon(
            modifier = Modifier
                .size(12.dp),
            painter = painterResource(id = R.drawable.ic_channel),
            contentDescription = null,
            tint = if (channel.hasUnread) {
                ChatTheme.colors.textHighEmphasis
            } else {
                ChatTheme.colors.textLowEmphasis
            },

            )

        Spacer(Modifier.width(16.dp))

        ChannelName(channel)
    }
}

@Preview(showBackground = true)
@Composable
fun GroupChannelItemPreview() {
    SlackTheme {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(ChatTheme.colors.appBackground)
        ) {
            DirectMessagingChannelItem(
                Channel(
                    watcherCount = 3,
                    extraData = mutableMapOf(
                        "name" to "test test",
                        "image" to "",
                    )
                ),
                {},
            )
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
fun DirectMessagingChannelItem(
    channel: Channel,
    onChannelClick: (Channel) -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .clickable { onChannelClick(channel) }
            .padding(vertical = 8.dp)
            .fillMaxWidth()
            .height(24.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Spacer(Modifier.width(10.dp))
        Box(
            modifier = modifier
                .clip(RoundedCornerShape(4.dp))
                .size(24.dp)
                .background(ChatTheme.colors.borders),
        ) {
            Text(
                modifier = Modifier
                    .align(Alignment.Center),
                text = "" + (channel.memberCount - 1),
                style = ChatTheme.typography.captionBold,
                fontSize = 12.sp,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                color = ChatTheme.colors.textHighEmphasis,
            )
        }
        Spacer(Modifier.width(10.dp))
        ChannelName(channel)
    }
}

/**
 * Component that represents a one-to-one channel item.
 *
 * @param channel The channel to display.
 * @param onChannelClick Handler for a single tap on an item.
 * @param modifier Modifier for styling.
 */
@Composable
fun OneToOneChannelItem(
    channel: Channel,
    onChannelClick: (Channel) -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .clickable { onChannelClick(channel) }
            .padding(vertical = 2.dp, horizontal = 16.dp)
            .fillMaxWidth()
            .height(40.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        val user = channel.getOtherUser()!!

        Box(
            modifier = Modifier
                .clip(ChatTheme.shapes.avatar)
                .size(36.dp),
        ) {
            UserAvatar(
                modifier = Modifier
                    .width(32.dp)
                    .height(32.dp)
                    .align(Alignment.Center),
                user = user
            )
            OnlineIndicator(
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .size(12.dp),
                isOnline = user.online
            )
        }

        Spacer(Modifier.width(10.dp))

        ChannelName(channel)

        val unreadCount = channel.unreadCount ?: 0
        if (unreadCount > 0) {
            UnreadCountBadge(unreadCount)
        }
    }
}

/**
 * Component that represents a channel name.
 *
 * @param channel The channel used to display the name.
 */
@Composable
fun ChannelName(channel: Channel) {
    Text(
        text = channel.getDisplayName(),
        style = ChatTheme.typography.body,
        maxLines = 1,
        overflow = TextOverflow.Ellipsis,
        color = ChatTheme.colors.textHighEmphasis,
        fontWeight = if (channel.hasUnread) FontWeight.Bold else FontWeight.Normal
    )
}
