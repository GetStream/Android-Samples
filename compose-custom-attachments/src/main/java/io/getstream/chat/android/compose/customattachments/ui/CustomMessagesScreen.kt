package io.getstream.chat.android.compose.customattachments.ui

import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.android.material.datepicker.MaterialDatePicker
import io.getstream.chat.android.client.models.Attachment
import io.getstream.chat.android.common.state.MessageMode
import io.getstream.chat.android.compose.customattachments.R
import io.getstream.chat.android.compose.handlers.SystemBackPressedHandler
import io.getstream.chat.android.compose.state.messages.SelectedMessageOptionsState
import io.getstream.chat.android.compose.state.messages.SelectedMessageReactionsState
import io.getstream.chat.android.compose.ui.components.messageoptions.defaultMessageOptionsState
import io.getstream.chat.android.compose.ui.components.selectedmessage.SelectedMessageMenu
import io.getstream.chat.android.compose.ui.components.selectedmessage.SelectedReactionsMenu
import io.getstream.chat.android.compose.ui.messages.MessagesScreen
import io.getstream.chat.android.compose.ui.messages.composer.MessageComposer
import io.getstream.chat.android.compose.ui.messages.header.MessageListHeader
import io.getstream.chat.android.compose.ui.messages.list.MessageList
import io.getstream.chat.android.compose.ui.theme.ChatTheme
import io.getstream.chat.android.compose.viewmodel.messages.MessageComposerViewModel
import io.getstream.chat.android.compose.viewmodel.messages.MessageListViewModel
import io.getstream.chat.android.compose.viewmodel.messages.MessagesViewModelFactory
import java.text.DateFormat
import java.util.Date

/**
 * A simplified version of the [MessagesScreen] component with the support
 * for event attachments.
 *
 * @param channelId The ID of the opened channel.
 * @param onBackPressed Handler for when the user taps on the Back button.
 */
@Composable
fun CustomMessagesScreen(
    channelId: String,
    onBackPressed: () -> Unit = {}
) {
    val factory = MessagesViewModelFactory(
        context = LocalContext.current,
        channelId = channelId,
    )

    val messageListViewModel = viewModel(MessageListViewModel::class.java, factory = factory)
    val composerViewModel = viewModel(MessageComposerViewModel::class.java, factory = factory)

    val selectedMessageState = messageListViewModel.currentMessagesState.selectedMessageState
    val messageMode = messageListViewModel.messageMode
    val currentUser by messageListViewModel.user.collectAsState()

    var eventDialogVisible by remember { mutableStateOf(false) }

    SystemBackPressedHandler(isEnabled = true, onBackPressed = onBackPressed)

    Box(modifier = Modifier.fillMaxSize()) {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            topBar = {
                MessageListHeader(
                    modifier = Modifier.height(56.dp),
                    channel = messageListViewModel.channel,
                    currentUser = currentUser,
                    messageMode = messageMode,
                    onBackPressed = onBackPressed,
                )
            },
            bottomBar = {
                CustomMessageComposer(
                    viewModel = composerViewModel,
                    onCreateEventClick = { eventDialogVisible = true }
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

        if (eventDialogVisible) {
            CreateEventDialog(
                onEventCreated = { eventTitle, eventDate ->
                    eventDialogVisible = false

                    val attachment = Attachment(
                        type = "event",
                        title = eventTitle,
                        extraData = mutableMapOf("date" to eventDate),
                    )
                    composerViewModel.addSelectedAttachments(listOf(attachment))
                },
                onDismiss = { eventDialogVisible = false }
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

/**
 * A custom [MessageComposer] with a button to trigger the visibility of [CreateEventDialog].
 *
 * @param viewModel The ViewModel that provides pieces of data to show in the composer, like the
 * currently selected integration data or the user input. It also handles sending messages.
 * @param onCreateEventClick Handler for the create event integration.
 */
@Composable
private fun CustomMessageComposer(
    viewModel: MessageComposerViewModel,
    onCreateEventClick: () -> Unit,
) {
    MessageComposer(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        viewModel = viewModel,
        integrations = {
            IconButton(
                modifier = Modifier
                    .size(48.dp)
                    .padding(12.dp),
                content = {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_calendar),
                        contentDescription = null,
                        tint = ChatTheme.colors.textLowEmphasis,
                    )
                },
                onClick = onCreateEventClick
            )
        },
    )
}

/**
 * Represents a modal dialog that allows the user to create an event.
 *
 * @param onEventCreated Handler called when a new event is created.
 * @param onDismiss Handler called when the dialog is dismissed.
 */
@Composable
private fun CreateEventDialog(
    onEventCreated: (String, String) -> Unit,
    onDismiss: () -> Unit = {},
) {
    val activity = LocalContext.current as AppCompatActivity

    var eventTitle: String by rememberSaveable { mutableStateOf("") }
    var eventDateMillis: Long by rememberSaveable { mutableStateOf(System.currentTimeMillis()) }

    val eventDate = DateFormat.getDateInstance().format(Date(eventDateMillis))

    Dialog(
        onDismissRequest = onDismiss,
        content = {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = ChatTheme.shapes.attachment,
                backgroundColor = ChatTheme.colors.barsBackground
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                ) {
                    OutlinedTextField(
                        modifier = Modifier.fillMaxWidth(),
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            textColor = ChatTheme.colors.textHighEmphasis,
                            unfocusedBorderColor = ChatTheme.colors.borders
                        ),
                        value = eventTitle,
                        onValueChange = { eventTitle = it },
                        textStyle = ChatTheme.typography.body,
                        placeholder = {
                            Text(
                                text = "Event title",
                                style = ChatTheme.typography.body
                            )
                        },
                        singleLine = true
                    )

                    OutlinedTextField(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable(
                                onClick = {
                                    MaterialDatePicker.Builder
                                        .datePicker()
                                        .setSelection(Date().time)
                                        .build()
                                        .apply {
                                            show(activity.supportFragmentManager, null)
                                            addOnPositiveButtonClickListener {
                                                eventDateMillis = it
                                            }
                                        }
                                }
                            ),
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            textColor = ChatTheme.colors.textHighEmphasis,
                            disabledTextColor = ChatTheme.colors.textHighEmphasis,
                            unfocusedBorderColor = ChatTheme.colors.borders
                        ),
                        value = eventDate,
                        onValueChange = {},
                        textStyle = ChatTheme.typography.body,
                        trailingIcon = {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_calendar),
                                contentDescription = null,
                                tint = ChatTheme.colors.borders,
                            )
                        },
                        enabled = false
                    )

                    TextButton(
                        modifier = Modifier.align(alignment = Alignment.End),
                        onClick = { onEventCreated(eventTitle, eventDate) }
                    ) {
                        Text(text = stringResource(R.string.stream_compose_ok))
                    }
                }
            }
        }
    )
}
