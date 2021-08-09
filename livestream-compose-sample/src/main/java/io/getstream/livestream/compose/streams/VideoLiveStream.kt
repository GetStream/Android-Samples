package io.getstream.livestream.compose.streams

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import io.getstream.chat.android.compose.ui.theme.ChatTheme
import io.getstream.chat.android.compose.viewmodel.messages.MessageComposerViewModel
import io.getstream.chat.android.compose.viewmodel.messages.MessageListViewModel
import io.getstream.livestream.compose.LiveStreamHeader
import io.getstream.livestream.compose.LiveStreamMessageList
import io.getstream.livestream.compose.LivestreamComposer
import io.getstream.livestream.compose.players.ExoVideoPlayer

/**
 * Shows a video view component that relies on [MessageListViewModel]
 * and [MessageComposerViewModel] to connect all the chat data handling operations.
 *
 * @param modifier - Modifier for styling.
 * @param urlToLoad - A string URL for loading a video in Video view component
 * @param composerViewModel - [MessageComposerViewModel] for manging message input field
 * @param listViewModel - [MessageListViewModel] The ViewModel that stores all the data and
 * business logic required to show a list of messages. The user has to provide one in this case,
 * as we require the channelId to start the operations.
 * @param onBackPressed - Handler for when the user clicks back press
 */
@Composable
fun VideoLiveStream(
    modifier: Modifier = Modifier,
    urlToLoad: String,
    composerViewModel: MessageComposerViewModel,
    listViewModel: MessageListViewModel,
    onBackPressed: () -> Unit
) {
    LaunchedEffect(Unit) {
        listViewModel.start()
    }

    ChatTheme {
        Box(modifier = modifier.fillMaxSize()) {
            LiveStreamHeader {
                onBackPressed()
            }
            ExoVideoPlayer(urlToLoad)
            Box(
                modifier = Modifier
                    .background(
                        brush = Brush.verticalGradient(
                            listOf(Color.Transparent, Color.Black),
                            0f,
                            1050f,
                        )
                    )
                    .align(Alignment.BottomCenter)
            ) {
                Column {
                    LiveStreamMessageList(listViewModel)
                    LivestreamComposer(composerViewModel)
                }
            }
            LiveStreamHeader {
                onBackPressed()
            }
        }
    }
}
