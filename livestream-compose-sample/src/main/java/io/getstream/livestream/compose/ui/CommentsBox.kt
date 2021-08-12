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
 *
 * @param
 */
@Composable
fun CommentsBox(
    composerViewModel: MessageComposerViewModel,
    listViewModel: MessageListViewModel,
    modifier: Modifier = Modifier,
) {
        Column(modifier = modifier) {
            MessageList(
                modifier = Modifier
                    .fillMaxHeight(0.5f), // dont do this
                viewModel = listViewModel,
                itemContent = {
                    LiveStreamMessage(messageItem = it)
                },
               // emp = {}
            )
            LivestreamComposer(composerViewModel = composerViewModel)
        }
}
