package io.getstream.livestream.compose.streams

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import io.getstream.chat.android.compose.ui.theme.ChatTheme
import io.getstream.chat.android.compose.viewmodel.messages.MessageComposerViewModel
import io.getstream.chat.android.compose.viewmodel.messages.MessageListViewModel
import io.getstream.livestream.compose.players.YoutubePlayer
import io.getstream.livestream.compose.ui.CommentsComponent
import io.getstream.livestream.compose.ui.LiveStreamHeader
import io.getstream.livestream.compose.ui.LivestreamComposer

/**
 * Shows a Youtube view component that relies on [MessageListViewModel]
 * and [MessageComposerViewModel] to connect all the chat data handling operations.
 *
 * @param modifier - Modifier for styling.
 * @param videoId - String id of Youtube video url
 * @param composerViewModel - [MessageComposerViewModel] for manging message input field
 * @param listViewModel - [MessageListViewModel] The ViewModel that stores all the data and
 * business logic required to show a list of messages. The user has to provide one in this case,
 * as we require the channelId to start the operations.
 * @param onBackPressed - Handler for when the user clicks back press
 */
@Composable
fun YoutubeLiveStream(
    modifier: Modifier = Modifier,
    videoId: String,
    composerViewModel: MessageComposerViewModel,
    listViewModel: MessageListViewModel,
    onBackPressed: () -> Unit
) {
    Box(modifier = modifier.fillMaxSize()) {
        Column(modifier) {
            YoutubePlayer(
                videoId = videoId
            )
            CommentsComponent(
                modifier = Modifier
                    .background(ChatTheme.colors.appBackground)
                    .weight(0.6f),
                composerViewModel = composerViewModel,
                listViewModel = listViewModel
            )
            LivestreamComposer(composerViewModel = composerViewModel)
        }
        LiveStreamHeader(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp, start = 16.dp)
        ) {
            onBackPressed()
        }
    }
}
