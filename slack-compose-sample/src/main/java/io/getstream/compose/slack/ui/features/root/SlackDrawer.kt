package io.getstream.compose.slack.ui.features.root

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import io.getstream.chat.android.compose.ui.theme.ChatTheme
import io.getstream.compose.slack.R
import io.getstream.compose.slack.models.DrawerWorkspaces

/**
 * A customizable drawer for integrating workspace dummy list [DrawerWorkspaces] in this showcase app.
 *
 * @param modifier - Modifier to customize the specs on the root element.
 */
@Composable
fun SlackDrawerContent(
    modifier: Modifier = Modifier,
    workspaces: List<DrawerWorkspaces>
) {
    Column(modifier = modifier) {
        Text(
            modifier = Modifier.padding(vertical = 24.dp, horizontal = 16.dp),
            text = stringResource(id = R.string.workspace_heading),
            style = ChatTheme.typography.title1
        )
        workspaces.forEach { screen ->
            SlackDrawerRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(96.dp)
                    .padding(vertical = 8.dp) // margins for each row corresponding to other rows
                    .clickable {
                        // No-op click
                    },
                screen = screen
            )
        }
    }
}

/** [WIP]
 * A view state less view component , representation of each drawer row based on [DrawerWorkspaces].
 *
 * @param modifier - Modifier to customize the specs on the root element.
 * @param screen - Each row representation of the drawer row element.
 */
@Composable
fun SlackDrawerRow(
    modifier: Modifier = Modifier,
    screen: DrawerWorkspaces
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            modifier = Modifier
                .padding(start = 16.dp)
                .width(56.dp)
                .height(56.dp)
                .clip(RoundedCornerShape(12.dp)),
            painter = painterResource(id = screen.image),
            contentDescription = stringResource(id = R.string.accessibility_workspace_image)
        )
        // Workspace text meta column
        Column(
            modifier = Modifier
                .padding(horizontal = 16.dp)
        ) {
            Text(
                modifier = Modifier.padding(top = 8.dp),
                text = screen.title,
                style = ChatTheme.typography.title3Bold
            )
            Text(
                modifier = Modifier.padding(bottom = 8.dp),
                text = screen.description,
                style = ChatTheme.typography.footnote
            )
        }
        Spacer(modifier = Modifier.width(72.dp)) // TODO fix or find better way to move Icon on right edge
        IconButton(
            onClick = {
                // No-op click
            }
        ) {
            Icon(
                imageVector = Icons.Filled.MoreVert,
                contentDescription = stringResource(id = R.string.accessibility_overflow_icon)
            )
        }
    }
}
