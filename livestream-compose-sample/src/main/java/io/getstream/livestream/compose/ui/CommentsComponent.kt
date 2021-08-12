package io.getstream.livestream.compose.ui

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
 * @param composerViewModel
 * @param listViewModel
 * @param modifier
 */
@Composable
fun CommentsComponent(
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
                LiveStreamComment(messageItem = it)
            },
            emptyContent = {
                // we hide EmptyView provided in SDK ,
                // as we have a transparent scrim background for the video playing
                // in the background of our message list
            }
        )
        LivestreamComposer(composerViewModel = composerViewModel)
    }
}
