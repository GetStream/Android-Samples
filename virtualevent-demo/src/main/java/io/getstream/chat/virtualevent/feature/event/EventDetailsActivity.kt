package io.getstream.chat.virtualevent.feature.event

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.getstream.sdk.chat.viewmodel.MessageInputViewModel
import com.getstream.sdk.chat.viewmodel.messages.MessageListViewModel
import io.getstream.chat.android.ui.message.input.viewmodel.bindView
import io.getstream.chat.android.ui.message.list.viewmodel.bindView
import io.getstream.chat.android.ui.message.list.viewmodel.factory.MessageListViewModelFactory
import io.getstream.chat.virtualevent.databinding.ActivityEventDetailsBinding
import io.getstream.chat.virtualevent.shared.message.LivestreamMessageViewHolderFactory

class EventDetailsActivity : AppCompatActivity() {

    private val cid: String = "livestream:data-strategy_cfe253a5-785b-4c77-a5a0-ec63693d4e58"
    private val factory: MessageListViewModelFactory by lazy { MessageListViewModelFactory(cid = cid) }
    private val messageListViewModel: MessageListViewModel by viewModels { factory }
    private val messageInputViewModel: MessageInputViewModel by viewModels { factory }
    private lateinit var binding: ActivityEventDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityEventDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
        }

        setupMessageListView()
        setupMessageInputView()
    }

    private fun setupMessageListView() {
        binding.messageListView.setMessageViewHolderFactory(LivestreamMessageViewHolderFactory())
        messageListViewModel.apply {
            bindView(binding.messageListView, this@EventDetailsActivity)
        }
    }

    private fun setupMessageInputView() {
        messageInputViewModel.apply {
            bindView(binding.messageInputView, this@EventDetailsActivity)
        }
    }
}