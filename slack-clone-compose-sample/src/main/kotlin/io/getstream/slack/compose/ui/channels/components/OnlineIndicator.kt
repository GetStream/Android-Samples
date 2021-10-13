package io.getstream.slack.compose.ui.channels.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.getstream.chat.android.compose.ui.common.avatar.UserAvatar
import io.getstream.chat.android.compose.ui.theme.ChatTheme
import io.getstream.slack.compose.ui.theme.SlackChannelsTheme

/**
 * Component that represents an online indicator to be used with [UserAvatar].
 *
 * By default, the indicator is a green dot when the user is online and a grey
 * dot when the user is offline.
 *
 * @param online If the user is online.
 * @param modifier Modifier for styling.
 * @param shape The shape of the online indicator.
 */
@Composable
fun OnlineIndicator(
    online: Boolean,
    modifier: Modifier = Modifier,
    shape: Shape = CircleShape,
) {
    val borderColor = if (online) ChatTheme.colors.infoAccent else ChatTheme.colors.disabled
    val indicatorColor = if (online) ChatTheme.colors.infoAccent else ChatTheme.colors.appBackground
    Box(
        modifier = modifier
            .border(2.5.dp, ChatTheme.colors.appBackground, shape)
            .padding(2.5.dp)
            .border(1.5.dp, borderColor, shape)
            .clip(shape)
            .background(indicatorColor)
    )
}

@Preview(showBackground = true)
@Composable
fun OnlineIndicatorPreviewOnline() {
    SlackChannelsTheme {
        OnlineIndicator(online = true)
    }
}

@Preview(showBackground = true)
@Composable
fun OnlineIndicatorPreviewOffline() {
    SlackChannelsTheme {
        OnlineIndicator(online = false)
    }
}
