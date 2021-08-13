package io.getstream.livestream.compose.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import io.getstream.chat.android.client.models.name
import io.getstream.chat.android.compose.state.messages.items.MessageItem
import io.getstream.chat.android.compose.ui.common.MessageBubble
import io.getstream.chat.android.compose.ui.common.avatar.UserAvatar
import io.getstream.chat.android.compose.ui.theme.ChatTheme
import io.getstream.livestream.compose.getTimeAgo
import java.text.SimpleDateFormat
import java.util.Date

/**
 * View component to add custom live stream channels screen.
 *
 * @param modifier - Modifier for styling.
 * @param messageItem - Data model for messageItem for each chat message row
 */
@Composable
fun LiveStreamComment(
    modifier: Modifier = Modifier,
    messageItem: MessageItem
) {
    val context = LocalContext.current
    Row(
        modifier = modifier
            .padding(start = 8.dp, bottom = 2.dp)
            .width(300.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        UserAvatar(
            modifier = Modifier
                .padding(start = 8.dp, end = 8.dp)
                .size(32.dp),
            user = messageItem.message.user
        )
        Column {
            Row {
                Text(
                    modifier = Modifier.padding(start = 8.dp),
                    text = messageItem.message.user.name,
                    style = ChatTheme.typography.bodyBold,
                    color = ChatTheme.colors.textHighEmphasis
                )
                Text(
                    modifier = Modifier.padding(start = 4.dp),
                    text = context.getTimeAgo(messageItem.message.createdAt ?: Date()),
                    style = ChatTheme.typography.footnote,
                    color = ChatTheme.colors.textLowEmphasis
                )
            }
            MessageBubble(
                color = ChatTheme.colors.appBackground,
                shape = ChatTheme.shapes.otherMessageBubble
            ) {
                Text(
                    modifier = Modifier.padding(8.dp),
                    text = messageItem.message.text,
                    style = ChatTheme.typography.body,
                    color = ChatTheme.colors.textHighEmphasis
                )
            }

        }
    }
}
