package io.getstream.chat.android.compose.customattachments.messages.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import io.getstream.chat.android.common.state.MessageMode
import io.getstream.chat.android.compose.state.messages.SelectedMessageOptionsState
import io.getstream.chat.android.compose.state.messages.SelectedMessageReactionsState
import io.getstream.chat.android.compose.ui.components.messageoptions.defaultMessageOptionsState
import io.getstream.chat.android.compose.ui.components.selectedmessage.SelectedMessageMenu
import io.getstream.chat.android.compose.ui.components.selectedmessage.SelectedReactionsMenu
import io.getstream.chat.android.compose.ui.messages.MessagesScreen
import io.getstream.chat.android.compose.ui.messages.list.MessageList
import io.getstream.chat.android.compose.ui.theme.ChatTheme
import io.getstream.chat.android.compose.viewmodel.messages.MessageComposerViewModel
import io.getstream.chat.android.compose.viewmodel.messages.MessageListViewModel
import io.getstream.chat.android.compose.viewmodel.messages.MessagesViewModelFactory

/**
 * A simplified version of the [MessagesScreen] with the support for event attachments.
 *
 * @param channelId The ID of the opened/active Channel.
 */
@Composable
fun CustomMessagesScreen(channelId: String) {
    val factory = MessagesViewModelFactory(
        context = LocalContext.current,
        channelId = channelId,
    )

    val messageListViewModel = viewModel(MessageListViewModel::class.java, factory = factory)
    val composerViewModel = viewModel(MessageComposerViewModel::class.java, factory = factory)

    val selectedMessageState = messageListViewModel.currentMessagesState.selectedMessageState
    val currentUser by messageListViewModel.user.collectAsState()

    var isShowingCreateEventDialog by remember { mutableStateOf(false) }

    Box(modifier = Modifier.fillMaxSize()) {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            bottomBar = {
                CustomMessageComposer(
                    viewModel = composerViewModel,
                    onCreateEventClick = {
                        isShowingCreateEventDialog = true
                    }
                )
            }
        ) {
            MessageList(
                modifier = Modifier
                    .padding(it)
                    .background(ChatTheme.colors.appBackground)
                    .fillMaxSize(),
                viewModel = messageListViewModel,
                onThreadClick = { message ->
                    composerViewModel.setMessageMode(MessageMode.MessageThread(message))
                    messageListViewModel.openMessageThread(message)
                },
            )
        }

        if (isShowingCreateEventDialog) {
            CreateEventDialog(
                onEventCreated = { attachment ->
                    isShowingCreateEventDialog = false
                    composerViewModel.addSelectedAttachments(listOf(attachment))
                },
                onDismiss = {
                    isShowingCreateEventDialog = false
                }
            )
        }

        if (selectedMessageState != null) {
            val selectedMessage = selectedMessageState.message
            if (selectedMessageState is SelectedMessageOptionsState) {
                SelectedMessageMenu(
                    modifier = Modifier.align(Alignment.BottomCenter),
                    messageOptions = defaultMessageOptionsState(
                        selectedMessage = selectedMessage,
                        currentUser = currentUser,
                        isInThread = messageListViewModel.isInThread
                    ),
                    message = selectedMessage,
                    onMessageAction = { action ->
                        composerViewModel.performMessageAction(action)
                        messageListViewModel.performMessageAction(action)
                    },
                    onDismiss = { messageListViewModel.removeOverlay() }
                )
            } else if (selectedMessageState is SelectedMessageReactionsState) {
                SelectedReactionsMenu(
                    modifier = Modifier.align(Alignment.BottomCenter),
                    message = selectedMessage,
                    currentUser = currentUser,
                    onMessageAction = { action ->
                        composerViewModel.performMessageAction(action)
                        messageListViewModel.performMessageAction(action)
                    },
                    onDismiss = { messageListViewModel.removeOverlay() }
                )
            }
        }
    }
}
