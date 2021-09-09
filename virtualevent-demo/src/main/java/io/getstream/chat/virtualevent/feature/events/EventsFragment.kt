package io.getstream.chat.virtualevent.feature.events

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.getstream.sdk.chat.viewmodel.MessageInputViewModel
import com.getstream.sdk.chat.viewmodel.messages.MessageListViewModel
import io.getstream.chat.android.ui.message.list.viewmodel.factory.MessageListViewModelFactory
import io.getstream.chat.virtualevent.databinding.FragmentEventsBinding

class EventsFragment : Fragment() {

    private val cid: String = "livestream:data-strategy_cfe253a5-785b-4c77-a5a0-ec63693d4e58"
    private val factory: MessageListViewModelFactory by lazy { MessageListViewModelFactory(cid = cid) }
    private val messageListViewModel: MessageListViewModel by viewModels { factory }
    private val messageInputViewModel: MessageInputViewModel by viewModels { factory }

    private var _binding: FragmentEventsBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEventsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

    }

}
