package io.getstream.livestream.compose.players

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.options.IFramePlayerOptions
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView

/**
 * View component which adds YoutubePlayer to play youtube videos.
 *
 * @param modifier - Modifier for styling.
 * @param videoId - String id of Youtube video url
 * @param playerOptions - Optional parameters for our Youtube player IFrameOptions,
 * default options already hides controls
 */
@Composable
fun YoutubePlayer(
    videoId: String,
    modifier: Modifier = Modifier,
    playerOptions: IFramePlayerOptions = IFramePlayerOptions.Builder()
        .controls(0)
        .rel(0)
        .build()
) {
    AndroidView(modifier = modifier.fillMaxWidth(), factory = { context ->
        val playerListener = object : AbstractYouTubePlayerListener() {
            override fun onReady(youTubePlayer: YouTubePlayer) {
                youTubePlayer.loadVideo(videoId = videoId, startSeconds = 0f)
            }
        }

        YouTubePlayerView(context).apply {
            enableAutomaticInitialization = false
            initialize(playerListener, false, playerOptions)
        }
    })
}
