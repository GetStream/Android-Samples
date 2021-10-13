package io.getstream.slack.compose.ui.messages

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import io.getstream.chat.android.compose.ui.theme.ChatTheme
import io.getstream.slack.compose.ui.theme.slackShapes
import io.getstream.slack.compose.ui.theme.slackTypography

/**
 * Customized [ChatTheme] for a visual parity with Slack.
 *
 * The custom theme overrides the default colors, typography and shapes
 * used by the SDK.
 */
@Composable
fun SlackMessagesTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    ChatTheme(
        colors = if (darkTheme) slackMessagesDarkColors() else slackMessagesLightColors(),
        typography = slackTypography,
        shapes = slackShapes()
    ) {
        content()
    }
}
