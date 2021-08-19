package io.getstream.compose.slack

import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import io.getstream.chat.android.compose.ui.theme.StreamShapes

/**
 * This class can be added with more Theme customizations for ChatTheme, such as [StreamShapes]
 */

@Composable
fun shapes(): StreamShapes {
    return StreamShapes(
        avatar = CircleShape,
        myMessageBubble = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp, bottomStart = 16.dp),
        otherMessageBubble = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp, bottomEnd = 16.dp),
        inputField = RoundedCornerShape(8.dp),
        attachment = RoundedCornerShape(16.dp)
    )
}
