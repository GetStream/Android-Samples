package io.getstream.livestream.compose.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import io.getstream.chat.android.compose.ui.theme.ChatTheme
import io.getstream.livestream.compose.R
import io.getstream.livestream.compose.topBarBackground

/**
 * View component to add support for scaffold with title,
 * top bar is always dark grey with white content.
 *
 * @param modifier - Modifier for styling.
 * @param onBackPressed - Handler for when the user clicks back press.
 * @param title - Title for the header.
 * @param onInfoClickHandler - Click handler to handle clicks on Info action icon
 * @param actions - Composable function to render actions on top bar.
 * @param content - Composable function to render content below the Scaffold.
 */
@Composable
fun YoutubeLiveStreamScaffold(
    modifier: Modifier = Modifier,
    onBackPressed: () -> Unit,
    title: String,
    onInfoClickHandler: () -> Unit = {},
    actions: @Composable (RowScope) -> Unit = {
        Icon(
            modifier = Modifier.clickable(
                onClick = {
                    onInfoClickHandler()
                }
            ),
            imageVector = Icons.Default.Info,
            contentDescription = stringResource(id = R.string.accessibilityBackButton),
            tint = colorResource(id = R.color.white),
        )
    },
    content: @Composable (PaddingValues) -> Unit
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = title,
                        color = colorResource(id = R.color.white),
                        style = ChatTheme.typography.body,
                    )
                },
                navigationIcon = {
                    Icon(
                        modifier = Modifier.clickable(
                            onClick = {
                                onBackPressed()
                            }
                        ),
                        painter = painterResource(id = R.drawable.ic_left_navigation),
                        contentDescription = stringResource(id = R.string.accessibilityBackButton),
                        tint = colorResource(id = R.color.white),
                    )
                },
                backgroundColor = topBarBackground(),
                contentColor = ChatTheme.colors.appBackground,
                elevation = 12.dp,
                actions = actions
            )
        },
        content = content
    )
}
