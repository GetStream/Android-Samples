package io.getstream.livestream.compose.players

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.options.IFramePlayerOptions
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView

/**
 * View component which adds YoutubePlayer to play youtube videos
 *
 * @param modifier - Modifier for styling.
 */
@Composable
fun YoutubePlayer(modifier: Modifier = Modifier) {
    AndroidView(modifier = modifier.fillMaxSize(), factory = { context ->
        val playerListener = object : AbstractYouTubePlayerListener() {
            override fun onReady(youTubePlayer: YouTubePlayer) {
                youTubePlayer.loadVideo(videoId = "XYqrrpvTtU8", startSeconds = 0f)
            }
        }

        val playerOptions = IFramePlayerOptions.Builder()
            .controls(0)
            .rel(0)
            .build()

        YouTubePlayerView(context).apply {
            enableAutomaticInitialization = false
            initialize(playerListener, false, playerOptions)
        }
    })
}