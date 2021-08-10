package io.getstream.livestream.compose

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material.Divider
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import io.getstream.chat.android.compose.ui.theme.ChatTheme

/**
 * View component to add support for Scaffold with actionable CTA
 *
 * @param title - Title for the header
 * @param modifier - Modifier for styling Scaffold root component.
 * @param actions - Composable function to render actions on top bar
 * @param content - Composable function to render content below the Scaffold
 */
@Composable
fun CustomHeader(
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

@Composable
fun PopupMenu(
    menuItems: List<String>,
    onClickCallbacks: List<() -> Unit>,
    showMenu: Boolean,
    onDismiss: () -> Unit,
) {
    DropdownMenu(
        expanded = showMenu,
        onDismissRequest = { onDismiss() },
    ) {
        menuItems.forEachIndexed { index, item ->
            DropdownMenuItem(onClick = {
                onDismiss()
                onClickCallbacks[index]
            }) {
                Text(text = item)
            }
        }
    }
}
