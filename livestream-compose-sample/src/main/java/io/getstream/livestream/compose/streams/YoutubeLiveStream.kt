package io.getstream.livestream.compose.streams

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import io.getstream.chat.android.compose.ui.messages.list.MessageList
import io.getstream.chat.android.compose.ui.theme.ChatTheme
import io.getstream.chat.android.compose.viewmodel.messages.MessageComposerViewModel
import io.getstream.chat.android.compose.viewmodel.messages.MessageListViewModel
import io.getstream.livestream.compose.players.YoutubePlayer
import io.getstream.livestream.compose.ui.LiveStreamComment
import io.getstream.livestream.compose.ui.LivestreamComposer
import io.getstream.livestream.compose.ui.YoutubeLiveStreamScaffold

/**
 * Shows a Youtube view component that relies on [MessageListViewModel]
 * and [MessageComposerViewModel] to connect all the chat data handling operations.
 *
 * @param modifier - Modifier for styling.
 * @param videoId - String id of Youtube video url
 * @param title - String to show title about current screen
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
    title: String,
    channelId: String,
    composerViewModel: MessageComposerViewModel,
    listViewModel: MessageListViewModel,
    onBackPressed: () -> Unit
) {
    YoutubeLiveStreamScaffold(
        modifier = modifier,
        title = title,
        onBackPressed = onBackPressed
    ) {
        // Content has a Column to show the following views in a vertical order:
        // [YoutubePlayer] - player to play youtube videos
        // [MessageList] - messagesList for loading comments
        // [LivestreamComposer] - custom message composer component to send comments
        Column {
            YoutubePlayer(
                modifier = Modifier.fillMaxWidth(),
                videoId = videoId
            )
            MessageList(
                modifier = Modifier
                    .background(ChatTheme.colors.appBackground)
                    .weight(0.6f),
                viewModel = listViewModel,
                itemContent = {
                    LiveStreamComment(messageItem = it, shouldShowBubble = false)
                },
                emptyContent = {
                    // we hide default EmptyView from SDK ,
                    // as we have a transparent scrim background for the video playing
                    // in the background of our message list

                    Spacer(
                        modifier = Modifier
                            .fillMaxHeight(0.8f)
                        // this allows the `empty` message to push down the column
                        // height for composer to be at bottom
                    )
                }
            )
            LivestreamComposer(
                channelId = channelId,
                composerViewModel = composerViewModel
            )
        }
    }
}
