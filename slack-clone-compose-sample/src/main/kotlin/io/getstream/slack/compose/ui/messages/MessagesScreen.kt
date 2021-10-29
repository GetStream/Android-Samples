package io.getstream.slack.compose.ui.messages

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import io.getstream.chat.android.compose.ui.messages.composer.MessageComposer
import io.getstream.chat.android.compose.ui.messages.header.MessageListHeader
import io.getstream.chat.android.compose.ui.messages.list.MessageList
import io.getstream.chat.android.compose.viewmodel.messages.AttachmentsPickerViewModel
import io.getstream.chat.android.compose.viewmodel.messages.MessageComposerViewModel
import io.getstream.chat.android.compose.viewmodel.messages.MessageListViewModel

@Composable
fun MessagesScreen(
    composerViewModel: MessageComposerViewModel,
    listViewModel: MessageListViewModel,
    attachmentsPickerViewModel: AttachmentsPickerViewModel,
    onBackPressed: () -> Unit = {},
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            Header(
                composerViewModel = composerViewModel,
                listViewModel = listViewModel,
                attachmentsPickerViewModel = attachmentsPickerViewModel,
                onBackPressed = onBackPressed
            )
        },
        bottomBar = {
            MessageComposer(viewModel = composerViewModel)
        }
    ) {
        MessageList(
            modifier = Modifier
                .fillMaxSize()
                .padding(it),
            viewModel = listViewModel,
        )
    }
}

@Composable
fun Header(
    composerViewModel: MessageComposerViewModel,
    listViewModel: MessageListViewModel,
    attachmentsPickerViewModel: AttachmentsPickerViewModel,
    onBackPressed: () -> Unit = {},
) {
    val user by listViewModel.user.collectAsState()
    val connectionState by listViewModel.connectionState.collectAsState()
    val messageMode = listViewModel.messageMode
    val backAction = {
        val isInThread = listViewModel.isInThread
        val isShowingOverlay = listViewModel.isShowingOverlay

        when {
            attachmentsPickerViewModel.isShowingAttachments -> attachmentsPickerViewModel.changeAttachmentState(
                false
            )
            isShowingOverlay -> listViewModel.selectMessage(null)
            isInThread -> {
                listViewModel.leaveThread()
                composerViewModel.leaveThread()
            }
            else -> onBackPressed()
        }
    }

    MessageListHeader(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp),
        channel = listViewModel.channel,
        currentUser = user,
        connectionState = connectionState,
        messageMode = messageMode,
        onHeaderActionClick = {},
        onBackPressed = backAction
    )
}
