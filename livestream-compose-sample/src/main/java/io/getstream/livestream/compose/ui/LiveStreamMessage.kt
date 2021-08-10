package io.getstream.livestream.compose.ui

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.getstream.chat.android.client.models.name
import io.getstream.chat.android.compose.state.messages.items.MessageItem
import io.getstream.chat.android.compose.ui.messages.list.MessageAvatar
import io.getstream.chat.android.compose.ui.theme.ChatTheme

/**
 * View component to add custom live stream channels screen
 *
 * @param modifier - Modifier for styling.
 * @param messageItem - Data model for messageItem for each chat message row
 */
@Composable
fun LiveStreamMessage(
    modifier: Modifier = Modifier,
    messageItem: MessageItem
) {
    val (message, position) = messageItem
    Row(
        modifier = modifier
            .padding(start = 8.dp, bottom = 2.dp)
            .widthIn(max = 300.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        MessageAvatar(position, message.user)
        Text(
            modifier = Modifier.padding(start = 8.dp),
            text = message.user.name,
            style = ChatTheme.typography.bodyBold,
            fontSize = 8.sp,
            color = ChatTheme.colors.textHighEmphasis
        )
        Text(
            modifier = Modifier.padding(8.dp),
            text = message.text,
            fontSize = 12.sp,
            color = ChatTheme.colors.textHighEmphasis
        )
    }
}
