package io.getstream.livestream.compose

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.getstream.chat.android.client.models.name
import io.getstream.chat.android.compose.state.messages.items.MessageItem
import io.getstream.chat.android.compose.ui.common.MessageBubble
import io.getstream.chat.android.compose.ui.theme.ChatTheme

@Composable
fun LiveStreamMessage(messageItem: MessageItem) {
    val (message, position) = messageItem
    Column(
        modifier = Modifier
            .padding(start = 8.dp, bottom = 2.dp)
            .widthIn(max = 300.dp)
    ) {
        MessageBubble(
            color = ChatTheme.colors.barsBackground,
            modifier = Modifier.padding(top = 2.dp),
            shape = RoundedCornerShape(
                topEnd = 16.dp,
                topStart = 0.dp,
                bottomEnd = 16.dp,
                bottomStart = 16.dp
            ),
            content = {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        modifier = Modifier.padding(start = 8.dp),
                        text = message.user.name,
                        style = ChatTheme.typography.bodyBold,
                        fontSize = 8.sp,
                        color = ChatTheme.colors.textLowEmphasis
                    )
                    Text(
                        modifier = Modifier.padding(8.dp),
                        text = message.text,
                        fontSize = 12.sp,
                        color = ChatTheme.colors.textHighEmphasis
                    )
                }
            }
        )
    }
}
