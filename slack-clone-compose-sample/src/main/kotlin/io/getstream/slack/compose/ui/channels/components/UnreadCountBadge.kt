package io.getstream.slack.compose.ui.channels.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.getstream.chat.android.compose.ui.theme.ChatTheme
import io.getstream.slack.compose.ui.theme.SlackChannelsTheme

/**
 * Component that represents a badge with the number of unread messages.
 *
 * @param unreadCount The number of unread messages.
 * @param modifier Modifier for styling.
 * @param badgeColor Color of the badge.
 * @param textColor Color of the unread count.
 * @param textStyle Shape of the badge.
 */
@Composable
fun UnreadCountBadge(
    unreadCount: Int,
    modifier: Modifier = Modifier,
    badgeColor: Color = ChatTheme.colors.errorAccent,
    textColor: Color = Color.White,
    textStyle: TextStyle = ChatTheme.typography.bodyBold,
) {
    val unreadCountText = if (unreadCount > 99) "99+" else unreadCount.toString()

    Box(
        modifier = modifier
            .widthIn(min = 24.dp)
            .heightIn(min = 24.dp)
            .background(
                shape = RoundedCornerShape(12.dp),
                color = badgeColor,
            )
    ) {
        Text(
            modifier = Modifier
                .align(Alignment.Center)
                .padding(2.dp),
            text = unreadCountText,
            style = textStyle,
            color = textColor,
        )
    }
}

@Preview(showBackground = true)
@Composable
fun UnreadCountBadgePreviewFew() {
    SlackChannelsTheme {
        UnreadCountBadge(unreadCount = 5)
    }
}

@Preview(showBackground = true)
@Composable
fun UnreadCountBadgePreviewMany() {
    SlackChannelsTheme {
        UnreadCountBadge(unreadCount = 150)
    }
}
