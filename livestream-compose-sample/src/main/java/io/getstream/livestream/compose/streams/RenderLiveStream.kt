package io.getstream.livestream.compose.streams

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import io.getstream.chat.android.compose.viewmodel.messages.MessageComposerViewModel
import io.getstream.chat.android.compose.viewmodel.messages.MessageListViewModel
import io.getstream.livestream.compose.models.LiveStreamType

/**
 * Composable function to load different types of livestream based on input variables.
 *
 * @param liveStreamType - [LiveStreamType] an enum representation of live stream type
 * for this channel.
 * @param composerViewModel - [MessageComposerViewModel] for manging message input field.
 * @param listViewModel - [MessageListViewModel] The ViewModel that stores all the data and
 * business logic required to show a list of messages. The user has to provide one in this case,
 * as we require the channelId to start the operations.
 * @param channelId - Channel id for loading comments and sending comments.
 * @param channelTitle - Channel name or a title to show in the header.
 * @param onBackPressed - Handler for when the user clicks back press.
 */
@ExperimentalPermissionsApi
@Composable
fun RenderLiveStream(
    liveStreamType: LiveStreamType,
    composerViewModel: MessageComposerViewModel,
    listViewModel: MessageListViewModel,
    channelId: String,
    channelTitle: String,
    onBackPressed: () -> Unit
) {
    when (liveStreamType) {
        LiveStreamType.Youtube -> {
            YoutubeLiveStream(
                modifier = Modifier
                    .fillMaxWidth(),
                videoId = "9rIy0xY99a0",
                title = channelTitle,
                composerViewModel = composerViewModel,
                listViewModel = listViewModel,
                channelId = channelId
            ) {
                onBackPressed()
            }
        }
        LiveStreamType.Camera -> {
            CameraLiveStream(
                composerViewModel = composerViewModel,
                listViewModel = listViewModel,
                channelId = channelId
            ) {
                onBackPressed()
            }
        }
        LiveStreamType.Video -> {
            VideoLiveStream(
                urlToLoad = "asset:///video.mp4",
                composerViewModel = composerViewModel,
                listViewModel = listViewModel,
                channelId = channelId
            ) {
                onBackPressed()
            }
        }
    }
}
