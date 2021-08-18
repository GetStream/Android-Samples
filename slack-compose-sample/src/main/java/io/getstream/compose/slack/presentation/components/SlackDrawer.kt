package io.getstream.compose.slack.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArtTrack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import io.getstream.chat.android.compose.ui.theme.ChatTheme

sealed class DrawerScreens(val title: String) {
    object Workspace1 : DrawerScreens("Workspace 1")
    object Workspace2 : DrawerScreens("Workspace 2")
    object Workspace3 : DrawerScreens("Workspace 3")
}

private val screens = listOf(
    DrawerScreens.Workspace1,
    DrawerScreens.Workspace2,
    DrawerScreens.Workspace3
)

/**
 * A customizable drawer for integrating workspace dummy list [DrawerScreens] in this showcase app.
 *
 * @param modifier - Modifier to customize the specs on the root element.
 */
@Composable
fun SlackDrawer(
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        screens.forEach { screen ->
            SlackDrawerRow(
                modifier = Modifier.height(24.dp),
                screen = screen
            )
        }
    }
}

/**
 * A view state less view component , representation of each drawer row based on [DrawerScreens].
 *
 * @param modifier - Modifier to customize the specs on the root element.
 * @param screen - Each row representation of the drawer row element.
 */
@Composable
fun SlackDrawerRow(
    modifier: Modifier = Modifier,
    screen: DrawerScreens
) {
    Spacer(modifier)
    Row {
        Image(
            modifier = Modifier
                .width(50.dp)
                .height(50.dp),
            imageVector = Icons.Filled.ArtTrack,
            contentDescription = ""
        )
        Text(
            text = screen.title,
            style = ChatTheme.typography.body
        )
    }
}
