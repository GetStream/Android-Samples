package io.getstream.compose.slack.ui.common

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

/**
 * A simple view component representation for User online status green indicator.
 *
 * @param modifier - Modifier for styling
 */
@Composable
fun OnlineStatus(
    isOnlineStatus: Boolean,
    modifier: Modifier = Modifier
) {
    // TODO Suggestion : Surface
    Box(
        modifier = modifier
            .clip(CircleShape)
            .background(if (isOnlineStatus) Color.Green else Color.Transparent)
            .border(1.dp, if (!isOnlineStatus) Color.Gray else Color.Transparent)
    )
}
