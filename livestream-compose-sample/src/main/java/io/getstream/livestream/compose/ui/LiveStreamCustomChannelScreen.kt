package io.getstream.livestream.compose.ui

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import io.getstream.chat.android.compose.ui.theme.ChatTheme

/**
 * View component to add support for Scaffold with actionable CTA and main content.
 *
 * @param title - Title for the header
 * @param modifier - Modifier for styling Scaffold root component.
 * @param actions - Composable function to render actions on top bar
 * @param content - Composable function to render content below the Scaffold
 */
@Composable
fun LiveStreamCustomChannelScreen(
    title: String,
    modifier: Modifier = Modifier,
    actions: @Composable (RowScope) -> Unit,
    content: @Composable (PaddingValues) -> Unit
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = title,
                        color = ChatTheme.colors.textHighEmphasis
                    )
                },
                backgroundColor = ChatTheme.colors.barsBackground,
                contentColor = ChatTheme.colors.appBackground,
                elevation = 12.dp,
                actions = actions
            )
        },
        content = content
    )
}
