package io.getstream.livestream.compose.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberImagePainter
import io.getstream.chat.android.client.models.image
import io.getstream.chat.android.client.models.name
import io.getstream.chat.android.compose.state.messages.items.MessageItem
import io.getstream.chat.android.compose.ui.common.avatar.Avatar
import io.getstream.chat.android.compose.ui.common.avatar.UserAvatar
import io.getstream.chat.android.compose.ui.theme.ChatTheme
import java.text.SimpleDateFormat
import java.util.*

/**
 * View component to add custom live stream channels screen
 *
 * @param modifier - Modifier for styling.
 * @param messageItem - Data model for messageItem for each chat message row
 */
@Composable
fun LiveStreamComment(
    modifier: Modifier = Modifier,
    messageItem: MessageItem
) {
    Row(
        modifier = modifier
            .padding(start = 8.dp, bottom = 2.dp)
            .widthIn(max = 300.dp),
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
                    text = SimpleDateFormat.getTimeInstance()
                        .format(messageItem.message.createdAt ?: Date()),
                    style = ChatTheme.typography.footnote
                )
            }


            Text(
                modifier = Modifier.padding(8.dp),
                text = messageItem.message.text,
                style = ChatTheme.typography.body,
                color = ChatTheme.colors.textHighEmphasis
            )
        }
    }
}
