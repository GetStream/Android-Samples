package io.getstream.chat.virtualevent.feature.event

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
import io.getstream.chat.virtualevent.shared.message.LivestreamMessageViewHolderFactory

class EventDetailsActivity : AppCompatActivity() {

    private lateinit var messageListViewModel: MessageListViewModel
    private lateinit var messageInputViewModel: MessageInputViewModel
    private lateinit var binding: ActivityEventDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityEventDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
        }

        val cid = intent.getStringExtra(KEY_EXTRA_CID)!!
        val factory = MessageListViewModelFactory(cid = cid)
        messageListViewModel = factory.create(MessageListViewModel::class.java)
        messageInputViewModel = factory.create(MessageInputViewModel::class.java)

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

    companion object {
        private const val KEY_EXTRA_CID = "extra_cid"
        const val cid1: String = "livestream:esg_data_f22160f7-01fd-423f-b622-fe7060ec10d8"
        const val cid2: String = "livestream:data-strategy_cfe253a5-785b-4c77-a5a0-ec63693d4e58"

        fun openActivity(context: Context, cid: String) {
            val intent = Intent(context, EventDetailsActivity::class.java).apply {
                putExtra(KEY_EXTRA_CID, cid)
            }
            context.startActivity(intent)
        }
    }
}
