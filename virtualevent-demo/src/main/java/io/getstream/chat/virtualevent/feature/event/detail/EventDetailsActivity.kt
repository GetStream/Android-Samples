package io.getstream.chat.virtualevent.feature.event.detail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.getstream.sdk.chat.viewmodel.MessageInputViewModel
import com.getstream.sdk.chat.viewmodel.messages.MessageListViewModel
import io.getstream.chat.android.ui.message.input.viewmodel.bindView
import io.getstream.chat.android.ui.message.list.viewmodel.bindView
import io.getstream.chat.android.ui.message.list.viewmodel.factory.MessageListViewModelFactory
import io.getstream.chat.virtualevent.databinding.ActivityEventDetailsBinding

/**
 * Activity that shows a live video stream and a list of comments
 * associated with this stream.
 */
class EventDetailsActivity : AppCompatActivity() {

    private lateinit var messageListViewModel: MessageListViewModel
    private lateinit var messageInputViewModel: MessageInputViewModel
    private lateinit var binding: ActivityEventDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEventDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val cid = intent.getStringExtra(KEY_EXTRA_CID)!!
        val factory = MessageListViewModelFactory(cid = cid)
        messageListViewModel = factory.create(MessageListViewModel::class.java)
        messageInputViewModel = factory.create(MessageInputViewModel::class.java)

        setupToolbar()
        setupMessageListView()
        setupMessageInputView()
    }

    private fun setupToolbar() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.apply {
            setDisplayShowHomeEnabled(true)
            setDisplayHomeAsUpEnabled(true)
            binding.toolbar.setNavigationOnClickListener {
                onBackPressed()
            }
        }
    }

    private fun setupMessageListView() {
        binding.messageListView.setMessageViewHolderFactory(LivestreamMessageItemVhFactory())
        messageListViewModel.apply {
            bindView(binding.messageListView, this@EventDetailsActivity)
        }
    }

    private fun setupMessageInputView() {
        messageInputViewModel.apply {
            bindView(binding.messageInputView, this@EventDetailsActivity)
        }
    }

    companion object {
        private const val KEY_EXTRA_CID: String = "extra_cid"

        fun createIntent(context: Context, cid: String): Intent {
            return Intent(context, EventDetailsActivity::class.java).putExtra(KEY_EXTRA_CID, cid)
        }
    }
}
