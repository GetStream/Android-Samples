package io.getstream.livestream.compose

import android.annotation.SuppressLint
import androidx.camera.core.CameraSelector
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import io.getstream.chat.android.compose.state.messages.Thread
import io.getstream.chat.android.compose.ui.messages.header.MessageListHeader
import io.getstream.chat.android.compose.ui.messages.list.MessageList
import io.getstream.chat.android.compose.ui.theme.ChatTheme
import io.getstream.chat.android.compose.viewmodel.messages.MessageComposerViewModel
import io.getstream.chat.android.compose.viewmodel.messages.MessageListViewModel

@Composable
fun CameraLiveStream(
    composerViewModel: MessageComposerViewModel,
    listViewModel: MessageListViewModel,
    onBackPressed: () -> Unit = {}
) {
    val lifecycleOwner = LocalLifecycleOwner.current
    val context = LocalContext.current
    val cameraProviderFuture = remember { ProcessCameraProvider.getInstance(context) }
    val currentState = listViewModel.currentMessagesState
    val messageMode = listViewModel.messageMode
    val isNetworkAvailable by listViewModel.isOnline.collectAsState()
    val user by listViewModel.user.collectAsState()
    LaunchedEffect(Unit) {
        listViewModel.start()
    }
    val width = remember { mutableStateOf(0f) }
    val height = remember { mutableStateOf(0f) }

    var sizeImage by remember { mutableStateOf(IntSize.Zero) }

    val gradient = Brush.verticalGradient(
        colors = listOf(Color.Transparent, Color.Black),
        startY = sizeImage.height.toFloat() / 3,  // 1/3
        endY = sizeImage.height.toFloat()
    )


    val backAction = {
        val isInThread = listViewModel.isInThread
        val isShowingOverlay = listViewModel.isShowingOverlay

        when {
            isShowingOverlay -> listViewModel.selectMessage(null)
            isInThread -> {
                listViewModel.leaveThread()
                composerViewModel.leaveThread()
            }
            else -> onBackPressed()
        }
    }

    SystemBackPressedHandler(isEnabled = true, onBackPressed = backAction)
    ChatTheme(isInDarkMode = true) {
        Box(modifier = Modifier.fillMaxSize()) {
            Scaffold(
                modifier = Modifier.fillMaxSize(),
                topBar = {
                    MessageListHeader(
                        modifier = Modifier
                            .height(56.dp)
                            .background(Color.Black.copy(alpha = 0.6f)),
                        channel = listViewModel.channel,
                        currentUser = user,
                        isNetworkAvailable = isNetworkAvailable,
                        messageMode = messageMode,
                        onBackPressed = backAction,
                        onHeaderActionClick = {}
                    )
                },
                bottomBar = {
                    MyCustomComposer(composerViewModel)
                }
            ) {
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
                Box {
                    MessageList(
                        modifier = Modifier
                            .padding(it)
                            .fillMaxSize()
                            .background(Color.Transparent),
                        viewModel = listViewModel,
                        onThreadClick = { message ->
                            composerViewModel.setMessageMode(Thread(message))
                            listViewModel.openMessageThread(message)
                        }
                    )
                }
                Box(modifier = Modifier
                    .matchParentSize()
                    .background(gradient))

                //TODO add a way to have a scrim sorted out on the box above Camera preview in which message list can be added to
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
