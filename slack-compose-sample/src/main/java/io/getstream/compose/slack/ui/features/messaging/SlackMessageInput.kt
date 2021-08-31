package io.getstream.compose.slack.ui.features.messaging

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.getstream.chat.android.client.models.Attachment
import io.getstream.chat.android.compose.state.messages.list.MessageAction
import io.getstream.chat.android.compose.ui.messages.composer.components.MessageInput
import io.getstream.chat.android.compose.ui.theme.ChatTheme
import io.getstream.compose.slack.R
import io.getstream.compose.slack.darkColors
import io.getstream.compose.slack.lightColors
import io.getstream.compose.slack.shapes

/**
 * A View component to provide message composer bar at bottom of a screen.
 *
 * @param onSendMessage - Handler when the input field value changes.
 * @param channelName - Name of the current channel or a DM to show an input hint.
 * @param attachments - Currently selected and visible list of attachments.
 * @param activeAction - Currently active action (for Edit UI).
 * @param modifier - Modifier for styling.
 * @param label  - The input field label (hint).
 * @param resetScroll - Callback for when Text field is focused.
 */
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun SlackMessageInput(
    onSendMessage: (String, List<Attachment>) -> Unit,
    channelName: String,
    attachments: List<Attachment>,
    activeAction: MessageAction?,
    modifier: Modifier = Modifier,
    label: @Composable () -> Unit = {
        DefaultInputLabel(channelName, modifier = Modifier)
    },
    onAttachmentsClick: () -> Unit = {},
    resetScroll: () -> Unit = {},
) {
    var messageText by remember { mutableStateOf("") }

    Column(modifier) {
        Divider(
            color = ChatTheme.colors.textLowEmphasis
        )
        MessageInput(
            modifier = Modifier
                .fillMaxWidth(),
            label = label,
            value = messageText,
            attachments = attachments,
            activeAction = activeAction,
            onValueChange = { messageText = it },
            onAttachmentRemoved = { }
        )

        val isInputValid = messageText.isNotEmpty() || attachments.isNotEmpty()

        UserInputSelector(
            sendMessageEnabled = isInputValid,
            onMessageSent = {
                onSendMessage(messageText, attachments)
                // Reset text field and close keyboard
                messageText = ""
                // Move scroll to bottom
                resetScroll()
            },
            onAttachmentsClick = onAttachmentsClick
        )
    }
}

@Preview(showBackground = true)
@Composable
fun UserInputPreview() {
    ChatTheme(
        shapes = shapes(),
        colors = if (isSystemInDarkTheme()) darkColors() else lightColors()
    ) {
        SlackMessageInput(
            modifier = Modifier.background(ChatTheme.colors.appBackground),
            channelName = "Channel name",
            onSendMessage = { _, _ -> },
            onAttachmentsClick = {},
            activeAction = null,
            attachments = emptyList()
        )
    }
}

/**
 * Default input field label that the user can override in [SlackMessageInput].
 *
 * @param channelName - Name of the current channel or a DM to show an input hint.
 * @param modifier - Modifier for styling.
 * */
@Composable
fun DefaultInputLabel(
    channelName: String,
    modifier: Modifier = Modifier
) {
    Text(
        modifier = modifier,
        textAlign = TextAlign.Center,
        text = stringResource(id = R.string.composer_hint, channelName),
        color = ChatTheme.colors.textLowEmphasis
    )
}

/**
 * Composable that represents the bottom half of message composer,
 * which includes attachments and send button.
 *
 * @param sendMessageEnabled - Boolean state to decide if message is valid.
 * @param onMessageSent - Handler for send button is clicked.
 * @param modifier - Modifier for styling.
 * @param onAttachmentsClick - Handler when the user selects attachments.
 * */
@Composable
private fun UserInputSelector(
    sendMessageEnabled: Boolean,
    onMessageSent: () -> Unit,
    modifier: Modifier = Modifier,
    onAttachmentsClick: () -> Unit = {}
) {
    Row(
        modifier = modifier
            .height(56.dp)
            .wrapContentHeight()
            .padding(horizontal = 4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Attachments
        IconButton(
            modifier = Modifier
                .padding(8.dp)
                .align(Alignment.CenterVertically)
                .clip(RoundedCornerShape(8.dp))
                .width(40.dp)
                .height(40.dp)
                .padding(8.dp),
            content = {
                Icon(
                    painter = painterResource(R.drawable.ic_attach),
                    contentDescription = stringResource(id = R.string.accessibility_photo),
                    tint = ChatTheme.colors.textHighEmphasis
                )
            },
            onClick = {
                onAttachmentsClick()
            }
        )
        Spacer(modifier = Modifier.weight(1f))

        // Send button
        IconButton(
            modifier = Modifier
                .padding(8.dp)
                .align(Alignment.CenterVertically)
                .clip(RoundedCornerShape(8.dp))
                .width(40.dp)
                .height(40.dp)
                .padding(8.dp),
            enabled = sendMessageEnabled,
            content = {
                Icon(
                    painter = painterResource(R.drawable.ic_send),
                    contentDescription = stringResource(id = R.string.accessibility_send),
                    tint = if (sendMessageEnabled) ChatTheme.colors.textHighEmphasis else ChatTheme.colors.disabled
                )
            },
            onClick = {
                if (sendMessageEnabled) {
                    onMessageSent()
                }
            }
        )
    }
}
