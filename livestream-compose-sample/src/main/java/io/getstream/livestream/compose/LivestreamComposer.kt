package io.getstream.livestream.compose

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Send
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import io.getstream.chat.android.compose.ui.messages.composer.components.MessageInput
import io.getstream.chat.android.compose.ui.theme.ChatTheme
import io.getstream.chat.android.compose.viewmodel.messages.MessageComposerViewModel

@Composable
fun LivestreamComposer(composerViewModel: MessageComposerViewModel) {
    Row(
        Modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        MessageInput(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 8.dp)
                .weight(1f),
            value = composerViewModel.input,
            attachments = emptyList(),
            activeAction = composerViewModel.activeAction,
            onValueChange = { composerViewModel.setMessageInput(it) },
            onAttachmentRemoved = {},
            label = {
                Row(
                    Modifier.wrapContentWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        modifier = Modifier.padding(start = 4.dp),
                        text = "Add your comments here",
                        color = ChatTheme.colors.textLowEmphasis
                    )
                }
            }
        )
        val isInputValid = composerViewModel.input.isNotEmpty()
        IconButton(
            enabled = isInputValid,
            content = {
                Icon(
                    imageVector = Icons.Default.Send,
                    contentDescription = stringResource(id = R.string.stream_compose_send_message),
                    tint = if (isInputValid) ChatTheme.colors.primaryAccent else ChatTheme.colors.textLowEmphasis
                )
            },
            onClick = {
                if (isInputValid) {
                    composerViewModel.sendMessage(composerViewModel.input)
                }
            }
        )
    }
}
