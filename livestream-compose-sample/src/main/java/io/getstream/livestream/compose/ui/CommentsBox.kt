package io.getstream.livestream.compose.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import io.getstream.chat.android.compose.ui.messages.list.MessageList
import io.getstream.chat.android.compose.viewmodel.messages.MessageComposerViewModel
import io.getstream.chat.android.compose.viewmodel.messages.MessageListViewModel

/**
 * A view component to build a custom message box with consists of [MessageList]
 * and a background scrim
 */
@Composable
fun CommentsBox(
    modifier: Modifier = Modifier,
    composerViewModel: MessageComposerViewModel,
    listViewModel: MessageListViewModel
) {
    Box(
        modifier = modifier
    ) {
        Column {
            MessageList(
                modifier = Modifier
                    .fillMaxHeight(0.5f),
                viewModel = listViewModel,
                itemContent = {
                    LiveStreamMessage(messageItem = it)
                },
                emptyState = {}
            )
            LivestreamComposer(composerViewModel = composerViewModel)
        }
    }
}
