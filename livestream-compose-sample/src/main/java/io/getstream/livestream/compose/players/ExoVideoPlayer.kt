package io.getstream.livestream.compose.players

import android.net.Uri
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import com.google.android.exoplayer2.C
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.ui.PlayerView
import com.google.android.exoplayer2.upstream.DataSource
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.google.android.exoplayer2.util.Util
import io.getstream.chat.android.compose.viewmodel.messages.MessageComposerViewModel
import io.getstream.chat.android.compose.viewmodel.messages.MessageListViewModel

/**
 * View component which adds Exoplayer to play external URL video links
 *
 * @param modifier - Modifier for styling.
 * @param urlToLoad - A string URL for loading a video
 */
@Composable
fun ExoVideoPlayer(
    urlToLoad: String,
    modifier: Modifier = Modifier
) {
    val localContext = LocalContext.current

    // Do not recreate the player everytime this Composable commits
    val exoPlayer = remember {
        SimpleExoPlayer.Builder(localContext)
            .build()
            .apply {
                val dataSourceFactory: DataSource.Factory = DefaultDataSourceFactory(
                    localContext,
                    Util.getUserAgent(localContext, localContext.packageName)
                )
                val mediaItem: MediaItem = MediaItem.fromUri(
                    Uri.parse(urlToLoad)
                )
                val source = ProgressiveMediaSource.Factory(dataSourceFactory)
                    .createMediaSource(mediaItem)

                this.setMediaSource(source)
                this.prepare()
            }
    }

    exoPlayer.videoScalingMode = C.VIDEO_SCALING_MODE_SCALE_TO_FIT_WITH_CROPPING
    exoPlayer.repeatMode = Player.REPEAT_MODE_ONE
    AndroidView(modifier = modifier.fillMaxWidth(), factory = { context ->
        PlayerView(context).apply {
            useController = false
            player = exoPlayer
            exoPlayer.playWhenReady = true
        }
    })

    DisposableEffect(key1 = urlToLoad) {
        onDispose {
            exoPlayer.release()
        }
    }
}
