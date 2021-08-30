package io.getstream.compose.slack.ui.features.messaging

import android.content.ClipboardManager
import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Chat
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.FileCopy
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import io.getstream.chat.android.client.ChatClient
import io.getstream.chat.android.client.models.Message
import io.getstream.chat.android.client.models.User
import io.getstream.chat.android.client.models.name
import io.getstream.chat.android.compose.state.messages.Thread
import io.getstream.chat.android.compose.state.messages.list.Copy
import io.getstream.chat.android.compose.state.messages.list.Delete
import io.getstream.chat.android.compose.state.messages.list.Edit
import io.getstream.chat.android.compose.state.messages.list.MessageOption
import io.getstream.chat.android.compose.state.messages.list.ThreadReply
import io.getstream.chat.android.compose.state.messages.list.buildMessageOption
import io.getstream.chat.android.compose.ui.common.SimpleDialog
import io.getstream.chat.android.compose.ui.messages.attachments.AttachmentsPicker
import io.getstream.chat.android.compose.ui.messages.composer.MessageComposer
import io.getstream.chat.android.compose.ui.messages.header.MessageListHeader
import io.getstream.chat.android.compose.ui.messages.list.MessageList
import io.getstream.chat.android.compose.ui.messages.overlay.SelectedMessageOverlay
import io.getstream.chat.android.compose.ui.theme.ChatTheme
import io.getstream.chat.android.compose.viewmodel.messages.AttachmentsPickerViewModel
import io.getstream.chat.android.compose.viewmodel.messages.MessageComposerViewModel
import io.getstream.chat.android.compose.viewmodel.messages.MessageListViewModel
import io.getstream.chat.android.compose.viewmodel.messages.MessagesViewModelFactory
import io.getstream.chat.android.offline.ChatDomain
import io.getstream.compose.slack.R

/**
 * Custom messaging screen component, that provides the necessary ViewModel to load channel messages
 * and composer functionality.
 *
 * This screen component loads custom message row.
 *
 * @param messageLimit - The limit of messages per query.
 * @param onBackPressed - Handler for screen back press.
 * @param onChannelInfoClicked - a click handler to handle clicks when this view is clicked
 * @param topBarTitleView - Custom composable component which is a representation of channel info.
 * @param channelId -  current selected channel ID to load messages from.
 * */
@Composable
fun SlackMessageScreen(
    messageLimit: Int = 30,
    onBackPressed: () -> Unit = {},
    onChannelInfoClicked: () -> Unit = {},
    topBarTitleView: @Composable (String) -> Unit = { title ->
        MessageHeaderView(title = title) {
            onChannelInfoClicked()
        }
    },
    channelId: String
) {
    val factory = buildViewModelFactory(LocalContext.current, channelId, messageLimit)
    val listViewModel = viewModel(MessageListViewModel::class.java, factory = factory)
    val composerViewModel = viewModel(MessageComposerViewModel::class.java, factory = factory)
    val attachmentsPickerViewModel =
        viewModel(AttachmentsPickerViewModel::class.java, factory = factory)

    val currentState = listViewModel.currentMessagesState
    val messageMode = listViewModel.messageMode
    val selectedMessage = currentState.selectedMessage
    val messageActions = listViewModel.messageActions
    val isShowingAttachments = attachmentsPickerViewModel.isShowingAttachments

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
    BackPressHandler(backAction)

    val isNetworkAvailable by listViewModel.isOnline.collectAsState()
    val user by listViewModel.user.collectAsState()

    Box(modifier = Modifier.fillMaxSize()) {
        Scaffold(
            topBar = {
                MessageListHeader(
                    modifier = Modifier
                        .height(56.dp),
                    channel = listViewModel.channel,
                    currentUser = user,
                    isNetworkAvailable = isNetworkAvailable,
                    messageMode = messageMode,
                    onBackPressed = backAction,
                    onHeaderActionClick = onChannelInfoClicked
                )

                // CustomMessageTopBar(topBarTitleView, listViewModel, backAction)
            },
            bottomBar = {
                MessageComposer(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight(),
                    viewModel = composerViewModel,
                    onAttachmentsClick = { attachmentsPickerViewModel.changeAttachmentState(true) },
                    onCancelAction = {
                        listViewModel.dismissAllMessageActions()
                        composerViewModel.dismissMessageActions()
                    }
                )
                // CustomMessageComposer(channelId, listViewModel, composerViewModel)
            }
        ) { paddingValues ->
            MessageList(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(paddingValues),
                currentState = listViewModel.currentMessagesState,
                itemContent = {
                    MessageCustomRow(
                        messageItem = it,
                        onLongItemClick = { message -> listViewModel.selectMessage(message) },
                        onThreadClick = { message ->
                            composerViewModel.setMessageMode(Thread(message))
                            listViewModel.openMessageThread(message)
                        })
                }
            )
        }


        if (selectedMessage != null) {
            SelectedMessageOverlay(
                messageOptions = customMessageOptions(
                    selectedMessage,
                    user,
                    listViewModel.isInThread
                ),
                message = selectedMessage,
                onMessageAction = { action ->
                    composerViewModel.performMessageAction(action)
                    listViewModel.performMessageAction(action)
                },
                onDismiss = { listViewModel.removeOverlay() }
            )
        }


        if (isShowingAttachments) {
            AttachmentsPicker(
                attachmentsPickerViewModel = attachmentsPickerViewModel,
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .height(350.dp),
                onAttachmentsSelected = { attachments ->
                    attachmentsPickerViewModel.changeAttachmentState(false)
                    composerViewModel.addSelectedAttachments(attachments)
                },
                onDismiss = {
                    attachmentsPickerViewModel.changeAttachmentState(false)
                    attachmentsPickerViewModel.dismissAttachments()
                }
            )
        }

        val deleteAction = messageActions.firstOrNull { it is Delete }

        if (deleteAction != null) {
            SimpleDialog(
                modifier = Modifier.padding(16.dp),
                title = stringResource(id = io.getstream.chat.android.compose.R.string.stream_compose_delete_message_title),
                message = stringResource(id = io.getstream.chat.android.compose.R.string.stream_compose_delete_message_text),
                onPositiveAction = { listViewModel.deleteMessage(deleteAction.message) },
                onDismiss = { listViewModel.dismissMessageAction(deleteAction) }
            )
        }
    }
}

/**
 * Custom message screen TopBar.
 *
 * @param topBarTitleView - Custom composable component which is a representation of channel info.
 * @param listViewModel - [MessageListViewModel] to load messages and threads.
 * @param backAction - Handler for screen back press.
 * @param modifier - Modifier for styling.
 * @param onChannelInfoClicked - a click handler to handle clicks when this view is clicked
 */
@Composable
private fun CustomMessageTopBar(
    topBarTitleView: @Composable (String) -> Unit,
    listViewModel: MessageListViewModel,
    backAction: () -> Unit,
    modifier: Modifier = Modifier,
    onChannelInfoClicked: () -> Unit = {},
) {
    TopAppBar(
        modifier = modifier,
        title = {
            topBarTitleView(listViewModel.channel.name)
        },
        backgroundColor = ChatTheme.colors.barsBackground,
        navigationIcon = {
            IconButton(
                modifier = Modifier.clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = rememberRipple(color = ChatTheme.colors.textHighEmphasis)
                ) {},
                onClick = {
                    backAction()
                }
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_left_navigation),
                    contentDescription = stringResource(id = R.string.accessibility_back),
                    tint = ChatTheme.colors.textHighEmphasis,
                )
            }
        },
        actions = {
            IconButton(
                modifier = Modifier.clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = rememberRipple(color = ChatTheme.colors.textHighEmphasis)
                ) {},
                onClick = {
                    onChannelInfoClicked()
                }
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_info),
                    contentDescription = stringResource(id = R.string.accessibility_icon),
                    tint = ChatTheme.colors.textHighEmphasis,
                )
            }
        }
    )
}

/**
 * Custom input composer.
 *
 * @param modifier - Modifier for styling.
 * @param channelId -  current selected channel ID to load messages from.
 * @param listViewModel - [MessageListViewModel] to load messages and threads.
 * @param composerViewModel - [MessageComposerViewModel] to send messages with text or attachment.
 */
@Composable
private fun CustomMessageComposer(
    modifier: Modifier = Modifier,
    channelId: String,
    listViewModel: MessageListViewModel,
    composerViewModel: MessageComposerViewModel
) {
    SlackMessageInput(
        modifier = modifier.background(ChatTheme.colors.barsBackground),
        channelName = listViewModel.channel.name,
        onMessageSent = {
            composerViewModel.sendMessage(Message(text = it, cid = channelId)) // WIP
        }
    )
}

/**
 * Builds the [MessagesViewModelFactory] required to run the Conversation/Messages screen.
 *
 * @param context - Used to build the [ClipboardManager].
 * @param channelId - The current channel ID, to load the messages from.
 * @param messageLimit - The limit when loading messages.
 * */
private fun buildViewModelFactory(
    context: Context,
    channelId: String,
    messageLimit: Int,
): MessagesViewModelFactory {
    val clipboardManager = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager

    return MessagesViewModelFactory(
        context,
        clipboardManager,
        ChatClient.instance(),
        ChatDomain.instance(),
        channelId,
        messageLimit
    )
}


/**
 * Builds the message options we show to our users.
 *
 * @param selectedMessage - Currently selected message, used to callbacks.
 * @param user - Current user, used to expose different states for messages.
 * @param inThread - If the message is in a thread or not, to block off some options.
 * */
@Composable
fun customMessageOptions(
    selectedMessage: Message,
    user: User?,
    inThread: Boolean,
): List<MessageOption> {
    val messageOptions: MutableList<MessageOption> = mutableListOf()

    if (selectedMessage.text.isNotEmpty() && selectedMessage.attachments.isEmpty()) {
        messageOptions.add(
            buildMessageOption(
                title = R.string.stream_compose_copy_message,
                icon = Icons.Default.FileCopy,
                action = Copy(selectedMessage)
            )
        )
    }

    if (!inThread) {
        messageOptions.add(
            buildMessageOption(
                title = R.string.stream_compose_thread_reply,
                icon = Icons.Default.Chat,
                action = ThreadReply(selectedMessage)
            )
        )
    }

    if (selectedMessage.user.id == user?.id) {
        messageOptions.add(
            buildMessageOption(
                title = R.string.stream_compose_edit_message,
                icon = Icons.Default.Edit,
                action = Edit(selectedMessage)
            )
        )

        messageOptions.add(
            MessageOption(
                title = R.string.stream_compose_delete_message,
                icon = Icons.Default.Delete,
                action = Delete(selectedMessage),
                iconColor = ChatTheme.colors.errorAccent,
                titleColor = ChatTheme.colors.errorAccent
            )
        )
    }

    return messageOptions
}
