package io.getstream.livestream.compose

import android.net.Uri
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.ui.PlayerView
import com.google.android.exoplayer2.upstream.DataSource
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.google.android.exoplayer2.util.Util

@Composable
fun VideoPlayer() {
    // This is the official way to access current context from Composable functions
    val context = LocalContext.current

    // Do not recreate the player everytime this Composable commits
    val exoPlayer = remember {
        SimpleExoPlayer.Builder(context).build().apply {
            val dataSourceFactory: DataSource.Factory = DefaultDataSourceFactory(
                context,
                Util.getUserAgent(context, context.packageName)
            )
            val mediaItem: MediaItem = MediaItem.fromUri(
                Uri.parse(
                    // Big Buck Bunny from Blender Project
                    "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4"
                )
            )
            val source = ProgressiveMediaSource.Factory(dataSourceFactory)
                .createMediaSource(mediaItem)

            this.setMediaSource(source)
        }
    }

    AndroidView(factory = { ctx ->
        //Example with Exoplayer
        PlayerView(context).apply {
            useController = true
            player = exoPlayer
            exoPlayer.playWhenReady = true
        }
    })
}
