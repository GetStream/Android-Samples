package io.getstream.livestream.compose

import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import io.getstream.chat.android.compose.ui.messages.list.MessageList
import io.getstream.chat.android.compose.viewmodel.messages.MessageListViewModel

@Composable
fun LiveStreamMessageList(messageListViewModel: MessageListViewModel) {
    MessageList(
        modifier = Modifier
            .fillMaxHeight(0.5f),
        viewModel = messageListViewModel,
        itemContent = {
            LiveStreamMessage(it)
        },
        emptyState = {}
    )
}
