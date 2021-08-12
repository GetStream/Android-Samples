package io.getstream.livestream.compose.ui

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Keyboard
import androidx.compose.material.icons.filled.Send
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import io.getstream.chat.android.compose.ui.messages.composer.MessageComposer
import io.getstream.chat.android.compose.ui.messages.composer.components.MessageInput
import io.getstream.chat.android.compose.ui.theme.ChatTheme
import io.getstream.chat.android.compose.viewmodel.messages.MessageComposerViewModel
import io.getstream.livestream.compose.R

/**
 * A View component to provide message composer bar at bottom of a screen
 *
 * @param modifier - Modifier for styling.
 * @param composerViewModel - Stream Message composer ViewModel to bind state for composer/ message input component
 */
@Composable
fun LivestreamComposer(
    modifier: Modifier = Modifier,
    composerViewModel: MessageComposerViewModel
) {
    Row(
        modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        MessageComposer(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            viewModel = composerViewModel,
            integrations = {},
            input = {
                MessageInput(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .padding(start = 8.dp),
                    value = composerViewModel.input,
                    attachments = composerViewModel.selectedAttachments,
                    activeAction = composerViewModel.activeAction,
                    onValueChange = { composerViewModel.setMessageInput(it) },
                    onAttachmentRemoved = { composerViewModel.removeSelectedAttachment(it) },
                    label = {
                        Row(
                            Modifier.wrapContentWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                modifier = Modifier.padding(start = 4.dp),
                                text = "Type something",
                                color = ChatTheme.colors.textLowEmphasis
                            )
                        }
                    }
                )
            }
        )
    }
}
