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
import io.getstream.whatsappclone.R
import io.getstream.whatsappclone.databinding.FragmentMessageListBinding
import com.getstream.sdk.chat.viewmodel.MessageInputViewModel
import com.getstream.sdk.chat.viewmodel.messages.MessageListViewModel
import io.getstream.chat.android.ui.message.list.header.viewmodel.MessageListHeaderViewModel
import io.getstream.chat.android.ui.message.list.viewmodel.bindView
import io.getstream.chat.android.ui.message.list.viewmodel.factory.MessageListViewModelFactory

class MessageListFragment : Fragment() {

    private val args: MessageListFragmentArgs by navArgs()

    val factory: MessageListViewModelFactory by lazy { MessageListViewModelFactory(args.cid) }
    val messageListHeaderViewModel: MessageListHeaderViewModel by viewModels { factory }
    val messageListViewModel: MessageListViewModel by viewModels { factory }
    val messageInputViewModel: MessageInputViewModel by viewModels { factory }

    lateinit var binding: FragmentMessageListBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMessageListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        messageListViewModel.bindView(binding.messageListView, this)

//        messageListViewModel.state.observe(this) { state ->
//            if (state is MessageListViewModel.State.NavigateUp) {
//                finish()
//            }
//        }
    }

    // enable the options menu
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_channel, menu)
        super.onCreateOptionsMenu(menu,inflater)
    }

    override fun onOptionsItemSelected(menuItem: MenuItem): Boolean {
        if (menuItem.itemId == android.R.id.home) {
            findNavController().navigateUp()
            return true
        }
        return false
    }

    // setup the toolbar and viewmodel
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val activity : AppCompatActivity = activity as AppCompatActivity

        // toolbar setup
        activity.setSupportActionBar(binding.toolbar)
        activity.supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
            setDisplayShowTitleEnabled(false)
        }

//        val client = StreamChat.getInstance(activity.application)
//        binding.lifecycleOwner = this
//        val channel = client.channel(args.channelType, args.channelId)
//        val factory = ChannelViewModelFactory(activity.application, channel)
//        val viewModel: ChannelViewModel by viewModels { factory }
//
//        // connect the view model
//        binding.viewModel = viewModel
//        binding.messageList.setViewModel(viewModel, this)
//        binding.messageInputView.setViewModel(viewModel, this)
//
//        view?.findViewById<MessageListView>(R.id.messageList)?.let {
//            val otherUsers: List<User> = channel.channelState.otherUsers
//            binding.avatarGroup.setChannelAndLastActiveUsers(channel, otherUsers, it.style)
//        }

//        binding.channelName.text = channel.name

    }
}
