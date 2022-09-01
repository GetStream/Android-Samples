/*
 *  The MIT License (MIT)
 *
 *  Copyright 2022 Stream.IO, Inc. All Rights Reserved.
 *
 *  Permission is hereby granted, free of charge, to any person obtaining a copy
 *  of this software and associated documentation files (the "Software"), to deal
 *  in the Software without restriction, including without limitation the rights
 *   to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 *   copies of the Software, and to permit persons to whom the Software is
 *  furnished to do so, subject to the following conditions:

 *  The above copyright notice and this permission notice shall be included in
 *  all copies or substantial portions of the Software.

 *  THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 *   IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 *  FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 *  AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 *  LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 *  OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 *  THE SOFTWARE.
 */

package io.getstream.chat.android.compose.customattachments.ui

import androidx.activity.compose.BackHandler
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.android.material.datepicker.MaterialDatePicker
import io.getstream.chat.android.client.models.Attachment
import io.getstream.chat.android.common.state.MessageMode
import io.getstream.chat.android.common.state.Reply
import io.getstream.chat.android.compose.customattachments.R
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
 * A custom [MessagesScreen] with the support for date attachments.
 *
 * @param channelId The ID of the opened channel.
 * @param onBackPressed Handler for the back action.
 */
@Composable
fun CustomMessagesScreen(
    channelId: String,
    onBackPressed: () -> Unit = {}
) {
    val factory = MessagesViewModelFactory(
        context = LocalContext.current,
        channelId = channelId
    )

    val messageListViewModel = viewModel(MessageListViewModel::class.java, factory = factory)
    val composerViewModel = viewModel(MessageComposerViewModel::class.java, factory = factory)

    val messageMode = messageListViewModel.messageMode
    val currentUser by messageListViewModel.user.collectAsState()

    BackHandler(enabled = true, onBack = onBackPressed)

    Box(modifier = Modifier.fillMaxSize()) {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            topBar = {
                MessageListHeader(
                    modifier = Modifier.height(56.dp),
                    channel = messageListViewModel.channel,
                    currentUser = currentUser,
                    messageMode = messageMode,
                    onBackPressed = onBackPressed
                )
            },
            bottomBar = {
                CustomMessageComposer(
                    viewModel = composerViewModel,
                    onDateSelected = {
                        val date = DateFormat
                            .getDateInstance(DateFormat.LONG)
                            .format(Date(it))
                        val attachment = Attachment(
                            type = "date",
                            extraData = mutableMapOf("payload" to date)
                        )

                        composerViewModel.addSelectedAttachments(listOf(attachment))
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
                onLongItemClick = {
                    composerViewModel.performMessageAction(Reply(it))
                }
            )
        }
    }
}

/**
 * A custom [MessageComposer] with a button that shows [MaterialDatePicker].
 *
 * @param viewModel The ViewModel that provides pieces of data to show in the composer, like the
 * currently selected integration data or the user input. It also handles sending messages.
 * @param onDateSelected Handler when the user selects a date.
 */
@Composable
private fun CustomMessageComposer(
    viewModel: MessageComposerViewModel,
    onDateSelected: (Long) -> Unit
) {
    val activity = LocalContext.current as AppCompatActivity

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
                        tint = ChatTheme.colors.textLowEmphasis
                    )
                },
                onClick = {
                    MaterialDatePicker.Builder
                        .datePicker()
                        .build()
                        .apply {
                            show(activity.supportFragmentManager, null)
                            addOnPositiveButtonClickListener {
                                onDateSelected(it)
                            }
                        }
                }
            )
        }
    )
}
