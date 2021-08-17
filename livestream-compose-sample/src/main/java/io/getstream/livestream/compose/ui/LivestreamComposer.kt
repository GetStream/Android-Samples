package io.getstream.livestream.compose.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import io.getstream.chat.android.client.models.Message
import io.getstream.chat.android.compose.ui.messages.composer.components.MessageInput
import io.getstream.chat.android.compose.ui.theme.ChatTheme
import io.getstream.chat.android.compose.viewmodel.messages.MessageComposerViewModel
import io.getstream.livestream.compose.R
import io.getstream.livestream.compose.cardBackground

/**
 * A View component to provide message composer bar at bottom of a screen.
 *
 * @param modifier - Modifier for styling.
 * @param channelId - Channel id should be passed in to send messages on given channel
 * @param composerViewModel - Stream Message composer ViewModel to bind state for composer/ message input component
 */
@Composable
fun LivestreamComposer(
    modifier: Modifier = Modifier,
    channelId: String,
    composerViewModel: MessageComposerViewModel
) {
    val context = LocalContext.current
    Row(
        modifier
            .fillMaxWidth()
            .padding(start = 8.dp, end = 8.dp, bottom = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        MessageInput(
            modifier = Modifier
                .fillMaxWidth()
                .height(40.dp)
                .weight(1f)
                .padding(start = 8.dp),
            value = composerViewModel.input,
            attachments = composerViewModel.selectedAttachments,
            activeAction = composerViewModel.activeAction,
            onValueChange = { composerViewModel.setMessageInput(it) },
            onAttachmentRemoved = { composerViewModel.removeSelectedAttachment(it) },
            label = {
                Text(
                    modifier = Modifier
                        .padding(start = 4.dp)
                        .wrapContentWidth()
                        .align(Alignment.CenterVertically),
                    text = stringResource(id = R.string.composer_hint),
                    color = ChatTheme.colors.textLowEmphasis
                )
            }
        )
        val inputValue = composerViewModel.input
        val isInputValid = inputValue.isNotEmpty()

        IconButton(
            modifier = Modifier
                .padding(8.dp)
                .align(Alignment.CenterVertically)
                .clip(RoundedCornerShape(8.dp))
                .background(context.cardBackground())
                .width(40.dp)
                .height(40.dp)
                .padding(8.dp),
            enabled = isInputValid,
            content = {
                Icon(
                    painter = painterResource(R.drawable.ic_arrow),
                    contentDescription = stringResource(id = R.string.stream_compose_send_message),
                    tint = if (isInputValid) ChatTheme.colors.textHighEmphasis else ChatTheme.colors.disabled
                )
            },
            onClick = {
                if (isInputValid) {
                    composerViewModel.sendMessage(
                        Message(
                            cid = channelId,
                            text = composerViewModel.input
                        )
                    )
                }
            }
        )
    }
}
