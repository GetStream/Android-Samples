package io.getstream.slack.compose.ui.messages

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
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
import io.getstream.chat.android.client.ChatClient
import io.getstream.chat.android.compose.ui.messages.composer.MessageComposer
import io.getstream.chat.android.compose.ui.messages.header.MessageListHeader
import io.getstream.chat.android.compose.ui.messages.list.MessageList
import io.getstream.chat.android.compose.ui.theme.ChatTheme
import io.getstream.chat.android.compose.viewmodel.messages.AttachmentsPickerViewModel
import io.getstream.chat.android.compose.viewmodel.messages.MessageComposerViewModel
import io.getstream.chat.android.compose.viewmodel.messages.MessageListViewModel
import io.getstream.chat.android.compose.viewmodel.messages.MessagesViewModelFactory
import io.getstream.chat.android.offline.ChatDomain

class MessagesActivity : AppCompatActivity() {
    private val factory: MessagesViewModelFactory by lazy {
        val channelId = "messaging:sample-app-channel-0" // TODO: obtain cid from Intent
        return@lazy MessagesViewModelFactory(
            context = this,
            channelId = channelId,
            chatClient = ChatClient.instance(),
            chatDomain = ChatDomain.instance(),
            enforceUniqueReactions = true,
            messageLimit = 30
        )
    }

    private val listViewModel by viewModels<MessageListViewModel>(factoryProducer = { factory })
    private val composerViewModel by viewModels<MessageComposerViewModel>(factoryProducer = { factory })
    val attachmentsPickerViewModel by viewModels<AttachmentsPickerViewModel>(factoryProducer = { factory })

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            ChatTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    topBar = {
                        Header(
                            listViewModel = listViewModel,
                            attachmentsPickerViewModel = attachmentsPickerViewModel
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
        }
    }

    @Composable
    fun Header(
        listViewModel: MessageListViewModel,
        attachmentsPickerViewModel: AttachmentsPickerViewModel
    ) {
        val user by listViewModel.user.collectAsState()
        val isNetworkAvailable by listViewModel.isOnline.collectAsState()
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
            isNetworkAvailable = isNetworkAvailable,
            messageMode = messageMode,
            onHeaderActionClick = {},
            onBackPressed = backAction
        )
    }

    companion object {
        private const val KEY_CHANNEL_ID = "channelId"

        fun getIntent(context: Context, channelId: String): Intent {
            return Intent(context, MessagesActivity::class.java).apply {
                putExtra(KEY_CHANNEL_ID, channelId)
            }
        }
    }
}
