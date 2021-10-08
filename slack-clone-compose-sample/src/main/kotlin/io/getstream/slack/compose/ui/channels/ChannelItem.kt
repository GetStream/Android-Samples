package io.getstream.slack.compose.ui.channels

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
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
import io.getstream.chat.android.client.models.User
import io.getstream.chat.android.compose.ui.common.avatar.UserAvatar
import io.getstream.chat.android.compose.ui.theme.ChatTheme
import io.getstream.chat.android.compose.ui.util.getDisplayName
import io.getstream.slack.compose.R
import io.getstream.slack.compose.ui.channels.components.OnlineIndicator
import io.getstream.slack.compose.ui.channels.components.UnreadCountBadge
import io.getstream.slack.compose.ui.util.getOtherUser
import io.getstream.slack.compose.ui.util.isDirectGroupChat
import io.getstream.slack.compose.ui.util.isDirectOneToOneChat

/**
 * Component that represents a channel item.
 *
 * There are 3 types of channel items that are displayed differently:
 * - Direct ono-to-one channel - displayed with user avatar, online indicator and unread count badge.
 * - Direct group channel - displayed with member counter and unread count badge.
 * - Regular named channel - displayed with a hash sign.
 *
 * @param channel The channel data to show.
 * @param onChannelClick Handler for a single tap on an item.
 */
@Composable
fun ChannelItem(
    channel: Channel,
    onChannelClick: (Channel) -> Unit
) {
    Box(
        modifier = Modifier
            .padding(horizontal = 8.dp)
            .fillMaxWidth()
            .height(45.dp),
    ) {
        Row(
            modifier = Modifier
                .clip(shape = RoundedCornerShape(6.dp))
                .clickable(onClick = { onChannelClick(channel) })
                .padding(horizontal = 8.dp)
                .fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            val isDirectOneToOneChat = channel.isDirectOneToOneChat()
            val isDirectGroupChat = channel.isDirectGroupChat()
            val hasUnread = channel.hasUnread

            when {
                isDirectOneToOneChat -> OneToOneAvatar(user = channel.getOtherUser()!!)
                isDirectGroupChat -> MemberCountBadge(memberCount = channel.memberCount)
                else -> HashIcon(isEmphasized = channel.hasUnread)
            }

            Text(
                modifier = Modifier
                    .weight(1f)
                    .wrapContentWidth(align = Alignment.Start),
                text = channel.getDisplayName(),
                style = ChatTheme.typography.body,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                color = if (hasUnread) {
                    ChatTheme.colors.textHighEmphasis
                } else {
                    ChatTheme.colors.textLowEmphasis
                },
                fontWeight = if (hasUnread) FontWeight.Bold else FontWeight.Normal
            )

            // unread count badge is not displayed for named channels
            if (isDirectOneToOneChat || isDirectGroupChat) {
                val unreadCount = channel.unreadCount ?: 0
                if (unreadCount > 0) {
                    UnreadCountBadge(unreadCount)
                }
            }
        }
    }
}

/**
 * Component that represents a user avatar with online indicator.
 *
 * @param user The user for the avatar.
 */
@Composable
private fun OneToOneAvatar(user: User) {
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
}

/**
 * Component that represents a badge with member count in the channel.
 *
 * @param memberCount Total members in the channel.
 */
@Composable
private fun MemberCountBadge(memberCount: Int) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(4.dp))
            .size(24.dp)
            .background(ChatTheme.colors.borders),
    ) {
        // the current user is not counted
        val otherMemberCount = memberCount - 1
        Text(
            modifier = Modifier.align(Alignment.Center),
            text = otherMemberCount.toString(),
            style = ChatTheme.typography.captionBold,
            fontSize = 12.sp,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            color = ChatTheme.colors.textHighEmphasis,
        )
    }
    Spacer(Modifier.width(12.dp))
}

/**
 * Component that represents a Slack style hash sign that used for the named channels.
 *
 * @param isEmphasized If the icon is highlighted.
 */
@Composable
private fun HashIcon(isEmphasized: Boolean) {
    Spacer(Modifier.width(2.dp))
    Icon(
        modifier = Modifier.size(16.dp),
        painter = painterResource(id = R.drawable.ic_channel),
        contentDescription = null,
        tint = if (isEmphasized) {
            ChatTheme.colors.textHighEmphasis
        } else {
            ChatTheme.colors.textLowEmphasis
        },
    )
    Spacer(Modifier.width(18.dp))
}
