package io.getstream.livestream.compose.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import io.getstream.chat.android.compose.ui.theme.ChatTheme
import io.getstream.livestream.compose.R

/**
 * View component to add support for header and a back button icon.
 *
 * @param modifier - Modifier for styling.
 * @param onBackPressed - Handler for when the user clicks back press
 */
@Composable
fun LiveStreamHeader(
    modifier: Modifier = Modifier,
    onBackPressed: () -> Unit
) {
    Box(modifier) {
        IconButton(
            modifier = Modifier
                .clip(CircleShape)
                .background(ChatTheme.colors.appBackground.copy(alpha = 0.4f)),
            onClick = onBackPressed
        ) {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = stringResource(id = R.string.accessibilityBackButton),
                tint = ChatTheme.colors.textHighEmphasis,
            )
        }
    }
}
