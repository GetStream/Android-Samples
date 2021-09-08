package io.getstream.chat.virtualevent.feature.events

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.getstream.sdk.chat.viewmodel.MessageInputViewModel
import com.getstream.sdk.chat.viewmodel.messages.MessageListViewModel
import io.getstream.chat.android.ui.message.input.viewmodel.bindView
import io.getstream.chat.android.ui.message.list.viewmodel.bindView
import io.getstream.chat.android.ui.message.list.viewmodel.factory.MessageListViewModelFactory
import io.getstream.chat.virtualevent.databinding.FragmentEventsBinding
import io.getstream.chat.virtualevent.shared.message.LivestreamMessageViewHolderFactory

class EventsFragment : Fragment() {

    private val cid: String = "livestream:data-strategy_cfe253a5-785b-4c77-a5a0-ec63693d4e58"
    private val factory: MessageListViewModelFactory by lazy { MessageListViewModelFactory(cid = cid) }
    private val messageListViewModel: MessageListViewModel by viewModels { factory }
    private val messageInputViewModel: MessageInputViewModel by viewModels { factory }

    private lateinit var eventsViewModel: EventsViewModel
    private var _binding: FragmentEventsBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        eventsViewModel = ViewModelProvider(this).get(EventsViewModel::class.java)

        _binding = FragmentEventsBinding.inflate(inflater, container, false)
        val root: View = binding.root

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initMessagesViewModel()
        initMessageInputViewModel()
    }

    private fun initMessagesViewModel() {
        binding.messageListView.setMessageViewHolderFactory(LivestreamMessageViewHolderFactory())
        messageListViewModel.apply {
            bindView(binding.messageListView, viewLifecycleOwner)
        }
    }

    private fun initMessageInputViewModel() {
        messageInputViewModel.apply {
            bindView(binding.messageInputView, viewLifecycleOwner)
            messageListViewModel.mode.observe(viewLifecycleOwner) {
                when (it) {
                    is MessageListViewModel.Mode.Thread -> {
                        messageInputViewModel.setActiveThread(it.parentMessage)
                    }
                    is MessageListViewModel.Mode.Normal -> {
                        messageInputViewModel.resetThread()
                    }
                }
            }
            binding.messageListView.setMessageEditHandler(::postMessageToEdit)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
