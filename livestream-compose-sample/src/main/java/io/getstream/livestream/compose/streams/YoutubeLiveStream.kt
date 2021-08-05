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
import io.getstream.livestream.compose.LiveStreamMessageList
import io.getstream.livestream.compose.LivestreamComposer
import io.getstream.livestream.compose.players.YoutubePlayer


@Composable
fun YoutubeLiveStream(
    composerViewModel: MessageComposerViewModel,
    listViewModel: MessageListViewModel
) {
    LaunchedEffect(Unit) {
        listViewModel.start()
    }

    ChatTheme {
        Box(modifier = Modifier.fillMaxSize()) {
            YoutubePlayer()
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
        }
    }
}
