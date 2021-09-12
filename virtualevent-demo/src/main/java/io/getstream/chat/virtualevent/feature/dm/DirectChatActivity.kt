package io.getstream.chat.virtualevent.feature.dm

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import androidx.activity.OnBackPressedCallback
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.descendants
import androidx.core.view.setPadding
import com.getstream.sdk.chat.viewmodel.MessageInputViewModel
import com.getstream.sdk.chat.viewmodel.messages.MessageListViewModel
import io.getstream.chat.android.ui.message.input.MessageInputView
import io.getstream.chat.android.ui.message.input.viewmodel.bindView
import io.getstream.chat.android.ui.message.list.MessageListView
import io.getstream.chat.android.ui.message.list.header.MessageListHeaderView
import io.getstream.chat.android.ui.message.list.header.viewmodel.MessageListHeaderViewModel
import io.getstream.chat.android.ui.message.list.header.viewmodel.bindView
import io.getstream.chat.android.ui.message.list.viewmodel.bindView
import io.getstream.chat.android.ui.message.list.viewmodel.factory.MessageListViewModelFactory
import io.getstream.chat.virtualevent.databinding.ActivityDirectChatBinding

class DirectChatActivity : AppCompatActivity() {

    private val cid: String by lazy {
        intent.getStringExtra(KEY_EXTRA_CID)!!
    }

    private val factory: MessageListViewModelFactory by lazy { MessageListViewModelFactory(cid) }
    private val messageListViewModel: MessageListViewModel by viewModels { factory }
    private val messageListHeaderViewModel: MessageListHeaderViewModel by viewModels { factory }
    private val messageInputViewModel: MessageInputViewModel by viewModels { factory }

    private lateinit var binding: ActivityDirectChatBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDirectChatBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupMessageListHeader(binding.messageListHeaderView)
        setupMessageList(binding.messageListView)
        setupMessageInput(binding.messageInputView)
    }

    private fun setupMessageListHeader(messageListHeaderView: MessageListHeaderView) {
        with(messageListHeaderView) {
            messageListHeaderViewModel.bindView(this, this@DirectChatActivity)

            val backHandler = {
                messageListViewModel.onEvent(MessageListViewModel.Event.BackButtonPressed)
            }
            setBackButtonClickListener(backHandler)
            onBackPressedDispatcher.addCallback(
                this@DirectChatActivity,
                object : OnBackPressedCallback(true) {
                    override fun handleOnBackPressed() {
                        backHandler()
                    }
                }
            )

            // Workaround to remove back button padding
            messageListHeaderView.descendants
                .firstOrNull { it is ImageView }
                ?.let {
                    it.setPadding(0)
                    it.background = null
                }
        }
    }

    private fun setupMessageList(messageListView: MessageListView) {
        messageListViewModel.bindView(messageListView, this@DirectChatActivity)

        messageListViewModel.state.observe(this@DirectChatActivity) {
            when (it) {
                is MessageListViewModel.State.Loading -> Unit
                is MessageListViewModel.State.Result -> Unit
                is MessageListViewModel.State.NavigateUp -> finish()
            }
        }
    }

    private fun setupMessageInput(messageInputView: MessageInputView) {
        messageInputViewModel.apply {
            messageInputViewModel.bindView(messageInputView, this@DirectChatActivity)

            messageListViewModel.mode.observe(this@DirectChatActivity) {
                when (it) {
                    is MessageListViewModel.Mode.Thread -> {
                        messageListHeaderViewModel.setActiveThread(it.parentMessage)
                        messageInputViewModel.setActiveThread(it.parentMessage)
                    }
                    is MessageListViewModel.Mode.Normal -> {
                        messageListHeaderViewModel.resetThread()
                        messageInputViewModel.resetThread()
                    }
                }
            }
            binding.messageListView.setMessageEditHandler(::postMessageToEdit)
        }
    }

    companion object {
        private const val KEY_EXTRA_CID: String = "extra_cid"

        fun createIntent(context: Context, cid: String): Intent {
            return Intent(context, DirectChatActivity::class.java).putExtra(KEY_EXTRA_CID, cid)
        }
    }
}
