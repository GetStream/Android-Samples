package io.getstream.slack.compose.ui.channels

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.getstream.chat.android.client.models.Channel
import io.getstream.chat.android.compose.ui.common.avatar.UserAvatar
import io.getstream.chat.android.compose.ui.theme.ChatTheme
import io.getstream.chat.android.compose.ui.util.getDisplayName
import io.getstream.slack.compose.R
import io.getstream.slack.compose.ui.channels.components.OnlineIndicator
import io.getstream.slack.compose.ui.channels.components.UnreadCount
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
            contentDescription = null
        )

        Spacer(Modifier.width(16.dp))

        ChannelName(channel)
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
        Text(
            modifier = Modifier
                .padding(horizontal = 16.dp),
            text = "" + (channel.memberCount - 1),
            style = if (channel.hasUnread) ChatTheme.typography.bodyBold else ChatTheme.typography.body,
            fontSize = 12.sp,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            color = ChatTheme.colors.textHighEmphasis,
        )

        ChannelName(channel)
    }
}

/**
 * Component that represents a one-to-one channel item.
 *
 * One-to-one cha
 *
 * / If the other user is online, then show the green presence indicator next to his name
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
            .padding(vertical = 8.dp)
            .fillMaxWidth()
            .height(24.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {

        val user = channel.getOtherUser()!!

        Box(
            modifier = Modifier
                .clip(ChatTheme.shapes.avatar)
                .size(24.dp),
        ) {
            UserAvatar(
                modifier = Modifier
                    .width(56.dp)
                    .height(56.dp),
                user = user
            )
            OnlineIndicator(
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .size(12.dp),
                isOnline = user.online
            )
        }

        ChannelName(channel)

        UnreadCount()
    }
}

@Composable
fun ChannelName(channel: Channel) {
    val textStyle = if (channel.hasUnread) {
        ChatTheme.typography.title3Bold
    } else {
        ChatTheme.typography.body
    }
    Text(
        text = channel.getDisplayName(),
        style = textStyle,
        maxLines = 1,
        overflow = TextOverflow.Ellipsis,
        color = ChatTheme.colors.textHighEmphasis,
    )
}
