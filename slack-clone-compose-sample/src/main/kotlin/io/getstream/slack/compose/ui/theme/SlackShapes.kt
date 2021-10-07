package io.getstream.slack.compose.ui.theme

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import io.getstream.chat.android.compose.ui.theme.StreamShapes

@Composable
fun slackShapes(): StreamShapes {
    return StreamShapes.defaultShapes().copy(
        avatar = RoundedCornerShape(4.dp),
        inputField = RoundedCornerShape(8.dp),
    )
}