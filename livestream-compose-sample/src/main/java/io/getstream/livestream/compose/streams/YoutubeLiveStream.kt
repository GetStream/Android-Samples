package io.getstream.livestream.compose.streams

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import io.getstream.chat.android.compose.ui.messages.list.MessageList
import io.getstream.chat.android.compose.ui.theme.ChatTheme
import io.getstream.chat.android.compose.viewmodel.messages.MessageComposerViewModel
import io.getstream.chat.android.compose.viewmodel.messages.MessageListViewModel
import io.getstream.livestream.compose.R
import io.getstream.livestream.compose.players.YoutubePlayer
import io.getstream.livestream.compose.topBarBackground
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
 * @param channelId - Channel id for loading comments and sending comments.
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
    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = title,
                        color = colorResource(id = R.color.white),
                        style = ChatTheme.typography.body,
                    )
                },
                navigationIcon = {
                    Icon(
                        modifier = Modifier
                            .padding(horizontal = 12.dp)
                            .clickable(
                                onClick = {
                                    onBackPressed()
                                }
                            ),
                        painter = painterResource(id = R.drawable.ic_left_navigation),
                        contentDescription = stringResource(id = R.string.accessibilityBackButton),
                        tint = colorResource(id = R.color.white),
                    )
                },
                backgroundColor = topBarBackground(),
                contentColor = ChatTheme.colors.appBackground,
                elevation = 12.dp,
                actions = {
                    Icon(
                        modifier = Modifier
                            .padding(horizontal = 12.dp)
                            .clickable(
                                onClick = {
                                    // Currently not handled
                                }
                            ),
                        painter = painterResource(id = R.drawable.ic_info),
                        contentDescription = stringResource(id = R.string.accessibilityBackButton),
                        tint = colorResource(id = R.color.white),
                    )
                }
            )
        },
        content = {
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
                    currentState = listViewModel.currentMessagesState,
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
                    modifier = Modifier.background(ChatTheme.colors.appBackground),
                    channelId = channelId,
                    composerViewModel = composerViewModel
                )
            }
        }
    )
}
