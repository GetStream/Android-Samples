package io.getstream.compose.slack.ui.features.messaging

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.getstream.chat.android.client.models.Message
import io.getstream.chat.android.client.models.User
import io.getstream.chat.android.client.models.name
import io.getstream.chat.android.compose.state.messages.items.MessageItem
import io.getstream.chat.android.compose.state.messages.items.None
import io.getstream.chat.android.compose.ui.common.avatar.UserAvatar
import io.getstream.chat.android.compose.ui.theme.ChatTheme
import io.getstream.compose.slack.shapes
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

/**
 * This is a component to render custom message or comments.
 *
 * @param modifier - Modifier for styling.
 * @param messageItem - Data model for messageItem for each chat message row
 */
@Composable
fun MessageCustomRow(
    modifier: Modifier = Modifier,
    messageItem: MessageItem
) {
    Row(
        modifier = modifier
            .padding(start = 8.dp, bottom = 2.dp),
        verticalAlignment = Alignment.Top
    ) {
        UserAvatar(
            modifier = Modifier
                .width(40.dp)
                .height(40.dp),
            user = messageItem.message.user
        )
        Column(modifier = Modifier.align(Alignment.CenterVertically)) {
            Row {
                Text(
                    modifier = Modifier
                        .padding(start = 8.dp)
                        .align(Alignment.CenterVertically),
                    text = messageItem.message.user.name,
                    style = ChatTheme.typography.title3Bold,
                    color = ChatTheme.colors.textHighEmphasis
                )
                Text(
                    modifier = Modifier
                        .padding(start = 4.dp)
                        .align(Alignment.CenterVertically),
                    text = SimpleDateFormat("hh:mm a", Locale.ENGLISH).format(
                        messageItem.message.createdAt ?: Date()
                    ),
                    style = ChatTheme.typography.footnote,
                    color = ChatTheme.colors.textLowEmphasis
                )
            }
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 8.dp, end = 16.dp),
                text = messageItem.message.text,
                style = ChatTheme.typography.body,
                color = ChatTheme.colors.textHighEmphasis
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
fun MessageItemPreview() {
    ChatTheme(shapes = shapes()) {
        MessageCustomRow(
            messageItem = MessageItem(
                message = Message(
                    id = "testId",
                    user = User(
                        id = "aditlal",
                        extraData = mutableMapOf(
                            "name" to "Adit Lal"
                        ),
                    ),
                    text = "Contrary to popular belief, Lorem Ipsum is not simply random text. It has roots in a piece of classical Latin literature from 45 BC, making it over 2000 years old. Richard McClintock, a Latin professor at Hampden-Sydney College in Virginia, looked up one of the more obscure Latin words, consectetur, from a Lorem Ipsum passage, and going through the cites of the word in classical literature, discovered the undoubtable source. Lorem Ipsum comes from sections 1.10.32 and 1.10.33 of \"de Finibus Bonorum et Malorum\" (The Extremes of Good and Evil) by Cicero, written in 45 BC. This book is a treatise on the theory of ethics, very popular during the Renaissance. The first line of Lorem Ipsum, \"Lorem ipsum dolor sit amet..\", comes from a line in section 1.10.32.\n" +
                            "\n"
                ),
                position = None
            )
        )
    }
}

