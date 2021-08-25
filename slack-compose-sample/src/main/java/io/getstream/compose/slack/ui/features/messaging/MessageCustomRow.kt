package io.getstream.compose.slack.ui.features.messaging

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import io.getstream.chat.android.client.models.Message
import io.getstream.chat.android.client.models.User
import io.getstream.chat.android.client.models.image
import io.getstream.chat.android.client.models.name
import io.getstream.chat.android.compose.state.messages.attachments.AttachmentState
import io.getstream.chat.android.compose.state.messages.items.MessageItem
import io.getstream.chat.android.compose.state.messages.items.None
import io.getstream.chat.android.compose.ui.common.avatar.Avatar
import io.getstream.chat.android.compose.ui.common.avatar.UserAvatar
import io.getstream.chat.android.compose.ui.theme.ChatTheme
import io.getstream.compose.slack.R
import io.getstream.compose.slack.shapes
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

/**
 * This is a component to render custom message or comments.
 *
 * @param messageItem - Data model for messageItem for each chat message row.
 * @param modifier - Modifier for styling.
 * @param onLongItemClick - Handler when the user selects a message, on long tap.
 * @param onThreadClick - Handler for thread clicks, if this message has a thread going.
 */
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MessageCustomRow(
    messageItem: MessageItem,
    modifier: Modifier = Modifier,
    onLongItemClick: (Message) -> Unit = {},
    onThreadClick: (Message) -> Unit = {}
) {
    val (message) = messageItem

    val attachmentFactory = ChatTheme.attachmentFactories.firstOrNull { it.canHandle(message) }
    val hasThread = message.threadParticipants.isNotEmpty()

    val clickModifier = Modifier.combinedClickable(
        interactionSource = remember { MutableInteractionSource() },
        indication = null,
        onClick = {
            if (hasThread) {
                onThreadClick(message)
            }
        },
        onLongClick = { onLongItemClick(message) }
    )

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
        // Content column
        Column(
            modifier = Modifier
                .align(Alignment.CenterVertically)
                .then(clickModifier)
        ) {
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
            if (message.text.isNotEmpty()) {
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 8.dp, end = 16.dp),
                    text = message.text,
                    style = ChatTheme.typography.body,
                    color = ChatTheme.colors.textHighEmphasis
                )
            }

            attachmentFactory?.factory?.invoke(
                AttachmentState(
                    modifier = Modifier.padding(4.dp),
                    message = messageItem,
                    onLongItemClick = onLongItemClick
                )
            )
            if (hasThread) {
                ThreadParticipants(modifier = modifier, participants = message.threadParticipants)
            }
        }
    }
}

/**
 * Shows a row of participants in the message thread, if they exist.
 *
 * @param participants - List of users in the thread.
 * @param modifier - Modifier for styling.
 * */
@Composable
internal fun ThreadParticipants(
    participants: List<User>,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .padding(start = 4.dp, end = 4.dp, top = 4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        for (user in participants) {
            val painter = rememberImagePainter(data = user.image)
            Avatar(
                modifier = Modifier
                    .padding(2.dp)
                    .size(24.dp),
                painter = painter
            )
        }
        Text(
            modifier = Modifier.padding(end = 4.dp),
            text = stringResource(id = R.string.message_footer_thread_counter, participants.size),
            style = ChatTheme.typography.footnoteBold,
            color = ChatTheme.colors.primaryAccent
        )
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
                    text = stringResource(id = R.string.dummy_data_message_text)
                ),
                position = None
            )
        )
    }
}

