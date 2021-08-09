package io.getstream.livestream.compose.streams

import android.annotation.SuppressLint
import androidx.camera.core.CameraSelector
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleOwner
import io.getstream.chat.android.compose.handlers.SystemBackPressedHandler
import io.getstream.chat.android.compose.ui.messages.list.MessageList
import io.getstream.chat.android.compose.ui.theme.ChatTheme
import io.getstream.chat.android.compose.viewmodel.messages.MessageComposerViewModel
import io.getstream.chat.android.compose.viewmodel.messages.MessageListViewModel
import io.getstream.livestream.compose.LiveStreamHeader
import io.getstream.livestream.compose.LiveStreamMessage
import io.getstream.livestream.compose.LiveStreamMessageList
import io.getstream.livestream.compose.LivestreamComposer

/**
 * Shows a camera preview surface in a view component that relies on [MessageListViewModel]
 * and [MessageComposerViewModel] to connect all the chat data handling operations.
 *
 * @param composerViewModel - [MessageComposerViewModel] for manging message input field
 * @param listViewModel - [MessageListViewModel] The ViewModel that stores all the data and
 * business logic required to show a list of messages. The user has to provide one in this case,
 * as we require the channelId to start the operations.
 * @param onBackPressed - Handler for when the user clicks back press
 */
@Composable
fun CameraLiveStream(
    composerViewModel: MessageComposerViewModel,
    listViewModel: MessageListViewModel,
    onBackPressed: () -> Unit
) {
    val lifecycleOwner = LocalLifecycleOwner.current
    val context = LocalContext.current
    val cameraProviderFuture = remember { ProcessCameraProvider.getInstance(context) }

    LaunchedEffect(Unit) {
        listViewModel.start()
    }

    ChatTheme {
        Box(modifier = Modifier.fillMaxSize()) {
            AndroidView(
                factory = { ctx ->
                    val preview = PreviewView(ctx)
                    val executor = ContextCompat.getMainExecutor(ctx)
                    cameraProviderFuture.addListener({
                        val cameraProvider = cameraProviderFuture.get()
                        bindPreview(
                            lifecycleOwner,
                            preview,
                            cameraProvider
                        )
                    }, executor)
                    preview
                },
                modifier = Modifier.fillMaxSize(),
            )
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


@SuppressLint("UnsafeExperimentalUsageError")
private fun bindPreview(
    lifecycleOwner: LifecycleOwner,
    previewView: PreviewView,
    cameraProvider: ProcessCameraProvider
) {
    val preview = Preview.Builder().build().also {
        it.setSurfaceProvider(previewView.surfaceProvider)
    }

    val cameraSelector = CameraSelector.Builder()
        .requireLensFacing(CameraSelector.LENS_FACING_BACK)
        .build()

    cameraProvider.unbindAll()
    cameraProvider.bindToLifecycle(
        lifecycleOwner,
        cameraSelector,
        preview
    )
}
