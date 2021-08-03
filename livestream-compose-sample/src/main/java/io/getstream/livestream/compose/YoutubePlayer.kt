package io.getstream.livestream.compose

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.options.IFramePlayerOptions
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView

@Composable
fun YTPlayer() {
    AndroidView(modifier = Modifier.fillMaxSize(), factory = { ctx ->
        val playerListener = object : AbstractYouTubePlayerListener() {
            override fun onReady(youTubePlayer: YouTubePlayer) {
                youTubePlayer.loadVideo(videoId = "qvDo0SKR8-k", startSeconds = 0f)
            }
        }

        val playerOptions = IFramePlayerOptions.Builder()
            .controls(0)
            .rel(0)
            .build()

        YouTubePlayerView(ctx).apply {
            enableAutomaticInitialization = false
            initialize(playerListener, false, playerOptions)
        }

    })
}
