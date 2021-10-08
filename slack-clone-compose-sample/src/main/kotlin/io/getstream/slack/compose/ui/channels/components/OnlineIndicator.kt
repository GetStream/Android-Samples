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
import io.getstream.slack.compose.ui.theme.SlackTheme

/**
 * Component that represents an online indicator to be used with [UserAvatar].
 *
 * @param isOnline - boolean toggle to update to either a green or grey dot.
 * @param modifier - Modifier for styling.
 * @param shape - The shape of the online indicator.
 */
@Composable
fun OnlineIndicator(
    isOnline: Boolean,
    modifier: Modifier = Modifier,
    shape: Shape = CircleShape,
) {
    val borderColor = if (isOnline) ChatTheme.colors.infoAccent else ChatTheme.colors.disabled
    val indicatorColor = if (isOnline) ChatTheme.colors.infoAccent else ChatTheme.colors.appBackground
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
    SlackTheme {
        OnlineIndicator(isOnline = true)
    }
}

@Preview(showBackground = true)
@Composable
fun OnlineIndicatorPreviewOffline() {
    SlackTheme {
        OnlineIndicator(isOnline = false)
    }
}
