package io.getstream.slack.compose.ui.theme

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import io.getstream.chat.android.compose.ui.theme.StreamShapes

@Composable
fun slackShapes(): StreamShapes {
    return StreamShapes(
        avatar = RoundedCornerShape(8.dp),
        myMessageBubble = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp, bottomStart = 16.dp),
        otherMessageBubble = RoundedCornerShape(
            topStart = 16.dp,
            topEnd = 16.dp,
            bottomEnd = 16.dp
        ),
        inputField = RoundedCornerShape(0.dp),
        attachment = RoundedCornerShape(8.dp),
        imageThumbnail = RoundedCornerShape(8.dp),
        bottomSheet = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)
    )
}