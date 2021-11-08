package io.getstream.whatsappclone.ui.message_list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.getstream.sdk.chat.viewmodel.MessageInputViewModel
import com.getstream.sdk.chat.viewmodel.messages.MessageListViewModel
import io.getstream.chat.android.ui.common.extensions.getDisplayName
import io.getstream.chat.android.ui.message.list.header.viewmodel.MessageListHeaderViewModel
import io.getstream.chat.android.ui.message.list.viewmodel.bindView
import io.getstream.chat.android.ui.message.list.viewmodel.factory.MessageListViewModelFactory
import io.getstream.whatsappclone.R
import io.getstream.whatsappclone.databinding.FragmentHomeBinding
import io.getstream.whatsappclone.databinding.FragmentMessageListBinding

class MessageListFragment : Fragment() {

    private val args: MessageListFragmentArgs by navArgs()

    private val factory: MessageListViewModelFactory by lazy { MessageListViewModelFactory(args.cid) }
    private val messageListHeaderViewModel: MessageListHeaderViewModel by viewModels { factory }
    private val messageListViewModel: MessageListViewModel by viewModels { factory }
    private val messageInputViewModel: MessageInputViewModel by viewModels { factory }

    private var _binding: FragmentMessageListBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_channel, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMessageListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        messageListViewModel.bindView(binding.messageListView, this)
        binding.messageInputView.initViews(
            sendMessage = { message -> messageInputViewModel.sendMessage(message) },
            keystroke = { messageInputViewModel.keystroke() }
        )

        messageListHeaderViewModel.channelState
            .observe(viewLifecycleOwner) {
                binding.channelNameTextView.text = it.getDisplayName(requireContext())
                binding.avatarView.setChannelData(it)
            }

        initToolbar()
    }

    override fun onOptionsItemSelected(menuItem: MenuItem): Boolean {
        if (menuItem.itemId == android.R.id.home) {
            findNavController().navigateUp()
            return true
        }
        return false
    }

    private fun initToolbar() {
        val activity: AppCompatActivity = activity as AppCompatActivity

        // toolbar setup
        activity.setSupportActionBar(binding.toolbar)
        activity.supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
            setDisplayShowTitleEnabled(false)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
