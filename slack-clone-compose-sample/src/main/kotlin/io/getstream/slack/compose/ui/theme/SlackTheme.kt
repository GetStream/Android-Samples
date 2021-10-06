package io.getstream.slack.compose.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import io.getstream.chat.android.compose.ui.theme.ChatTheme

/**
 * Customized [ChatTheme] for a visual parity with Slack.
 *
 * The custom theme overrides the default colors, typography and shapes
 * used by the SDK.
 */
@Composable
fun SlackTheme(content: @Composable () -> Unit) {
    ChatTheme(
        colors = if (isSystemInDarkTheme()) slackDarkColors() else slackLightColors(),
        typography = slackTypography,
        shapes = slackShapes()
    ) {
        content()
    }
}
