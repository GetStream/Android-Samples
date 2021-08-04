package io.getstream.livestream.compose.streams

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import io.getstream.chat.android.compose.handlers.SystemBackPressedHandler
import io.getstream.chat.android.compose.state.messages.Thread
import io.getstream.chat.android.compose.ui.messages.header.MessageListHeader
import io.getstream.chat.android.compose.ui.messages.list.MessageList
import io.getstream.chat.android.compose.ui.theme.ChatTheme
import io.getstream.chat.android.compose.viewmodel.messages.MessageComposerViewModel
import io.getstream.chat.android.compose.viewmodel.messages.MessageListViewModel
import io.getstream.livestream.compose.MyCustomComposer
import io.getstream.livestream.compose.YTPlayer


@Composable
fun YoutubeLiveStream(
    composerViewModel: MessageComposerViewModel,
    listViewModel: MessageListViewModel,
    onBackPressed: () -> Unit = {}
) {
    val currentState = listViewModel.currentMessagesState
    val messageMode = listViewModel.messageMode
    val isNetworkAvailable by listViewModel.isOnline.collectAsState()
    val user by listViewModel.user.collectAsState()
    LaunchedEffect(Unit) {
        listViewModel.start()
    }

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
                YTPlayer()
                Surface(
                    color = Color.Black.copy(alpha = 0.6f),
                    modifier = Modifier.fillMaxSize()
                ){
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
            }
        }
    }
}
