package io.getstream.compose.slack.ui.features.messaging

import android.content.ClipboardManager
import android.content.Context
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.TopAppBar
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import io.getstream.chat.android.client.ChatClient
import io.getstream.chat.android.client.models.Message
import io.getstream.chat.android.client.models.name
import io.getstream.chat.android.compose.state.messages.Thread
import io.getstream.chat.android.compose.ui.messages.composer.MessageComposer
import io.getstream.chat.android.compose.ui.messages.list.MessageList
import io.getstream.chat.android.compose.ui.theme.ChatTheme
import io.getstream.chat.android.compose.viewmodel.messages.AttachmentsPickerViewModel
import io.getstream.chat.android.compose.viewmodel.messages.MessageComposerViewModel
import io.getstream.chat.android.compose.viewmodel.messages.MessageListViewModel
import io.getstream.chat.android.compose.viewmodel.messages.MessagesViewModelFactory
import io.getstream.chat.android.offline.ChatDomain
import io.getstream.compose.slack.R

/**
 * Default messaging screen component, that provides the necessary ViewModel to load channel messages
 * and composer functionality.
 *
 * It can be used without providing most parameters to achieve a default behavior
 * or it can be tweaked if necessary.
 *
 * @param messageLimit - The limit of messages per query.
 * @param onBackPressed - Handler for screen back press.
 * @param onChannelInfoClicked - a click handler to handle clicks when this view is clicked
 * @param topBarTitleView - Custom composable component which is a representation of channel info.
 * @param channelId -  current selected channel ID to load messages from.
 * */
@Composable
fun MessageScreen(
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
    BackPressHandler(backAction)

    Scaffold(
        topBar = {
            TopAppBar(
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
                    onThreadClick = { message ->
                        composerViewModel.setMessageMode(Thread(message))
                        listViewModel.openMessageThread(message)
                    })
            }
        )
    }
}

/**
 * Custom input composer
 */
@Composable
fun CustomMessageComposer(
    channelId: String,
    listViewModel: MessageListViewModel,
    composerViewModel: MessageComposerViewModel
) {
    CustomInput(
        modifier = Modifier.background(ChatTheme.colors.barsBackground),
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
