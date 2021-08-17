package io.getstream.slackclonesample.feature.chat

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.getstream.sdk.chat.viewmodel.MessageInputViewModel
import com.getstream.sdk.chat.viewmodel.messages.MessageListViewModel
import com.getstream.sdk.chat.viewmodel.messages.getCreatedAtOrThrow
import io.getstream.chat.android.client.errors.ChatNetworkError
import io.getstream.chat.android.client.models.Flag
import io.getstream.chat.android.client.models.Message
import io.getstream.chat.android.client.utils.Result
import io.getstream.chat.android.ui.message.list.header.viewmodel.MessageListHeaderViewModel
import io.getstream.chat.android.ui.message.list.viewmodel.bindView
import io.getstream.chat.android.ui.message.list.viewmodel.factory.MessageListViewModelFactory
import io.getstream.slackclonesample.databinding.FragmentChatBinding
import io.getstream.slackclonesample.feature.chat.viewholder.SlackMessageViewHolderFactory
import io.getstream.slackclonesample.feature.home.HomeViewModel
import io.getstream.slackclonesample.util.useAdjustResize
import kotlinx.coroutines.flow.collect
import java.util.*

class ChatFragment : Fragment() {

    private val cid: String by lazy {
        requireArguments().getString("cid")
            ?: throw IllegalArgumentException("cid argument can't be null")
    }
    private val factory: MessageListViewModelFactory by lazy { MessageListViewModelFactory(cid = cid) }
    private val headerViewModel: MessageListHeaderViewModel by viewModels { factory }
    private val messageListViewModel: MessageListViewModel by viewModels { factory }
    private val messageInputViewModel: MessageInputViewModel by viewModels { factory }

    private val homeViewModel: HomeViewModel by activityViewModels()

    private var _binding: FragmentChatBinding? = null
    private val binding get() = _binding!!

    private val maxAttachmentFile: Int = 20
    private var messageListSubtitle: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycleScope.launchWhenStarted {
            homeViewModel.state.collect(::renderState)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentChatBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.messageListHeaderView.root.setNavigationOnClickListener {
            homeViewModel.submitAction(HomeViewModel.Action.OpenDrawerAction)
        }
        initMessagesViewModel()
        configureMessageInputView()
        initMessageInputViewModel()
        configureBackButtonHandling()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun configureMessageInputView() {
        lifecycleScope.launchWhenCreated {
            /*binding.messageInputView.listenForBigAttachments(
                object : MessageInputView.BigFileSelectionListener {
                    override fun handleBigFileSelected(hasBigFile: Boolean) {
                        *//*if (hasBigFile) {
                            messageListSubtitle = binding.messagesHeaderView.getOnlineStateSubtitle()
                            binding.messagesHeaderView.setOnlineStateSubtitle(
                                requireContext().getString(
                                    R.string.chat_fragment_big_attachment_subtitle,
                                    maxAttachmentFile
                                )
                            )
                        } else {
                            messageListSubtitle?.let(binding.messagesHeaderView::setOnlineStateSubtitle)
                        }*//*
                    }
                }
            )*/
        }
    }

    override fun onResume() {
        super.onResume()
        useAdjustResize()
    }

    private fun renderState(state: HomeViewModel.State) {
        state.userId?.let {
            headerViewModel.bindView(
                binding.messageListHeaderView.root, viewLifecycleOwner,
                state.userId
            )
        }
    }

    private fun configureBackButtonHandling() {
        activity?.apply {
            onBackPressedDispatcher.addCallback(
                viewLifecycleOwner,
                object : OnBackPressedCallback(true) {
                    override fun handleOnBackPressed() {
                        messageListViewModel.onEvent(MessageListViewModel.Event.BackButtonPressed)
                    }
                }
            )
        }
        /*binding.messagesHeaderView.setBackButtonClickListener {
            messageListViewModel.onEvent(MessageListViewModel.Event.BackButtonPressed)
        }*/
    }

    private fun initMessageInputViewModel() {
        messageInputViewModel.apply {
            bindView(binding.messageInputView, viewLifecycleOwner)
            messageListViewModel.mode.observe(viewLifecycleOwner) {
                when (it) {
                    is MessageListViewModel.Mode.Thread -> {
                        headerViewModel.setActiveThread(it.parentMessage)
                        messageInputViewModel.setActiveThread(it.parentMessage)
                    }
                    is MessageListViewModel.Mode.Normal -> {
                        headerViewModel.resetThread()
                        messageInputViewModel.resetThread()
                    }
                }
            }
            binding.messageListView.setMessageEditHandler(::postMessageToEdit)
        }
    }

    private fun initMessagesViewModel() {
        val calendar = Calendar.getInstance()
        binding.messageListView.setMessageViewHolderFactory(SlackMessageViewHolderFactory())
        messageListViewModel.apply {
            bindView(binding.messageListView, viewLifecycleOwner)
            setDateSeparatorHandler { previousMessage, message ->
                if (previousMessage == null) {
                    true
                } else {
                    shouldShowDateSeparator(calendar, previousMessage, message)
                }
            }
            setThreadDateSeparatorHandler { previousMessage, message ->
                if (previousMessage == null) {
                    false
                } else {
                    shouldShowDateSeparator(calendar, previousMessage, message)
                }
            }
            state.observe(viewLifecycleOwner) {
                when (it) {
                    is MessageListViewModel.State.Loading -> Unit
                    is MessageListViewModel.State.Result -> Unit
                }
            }
        }
    }

    private fun shouldShowDateSeparator(
        calendar: Calendar,
        previousMessage: Message,
        message: Message
    ): Boolean {
        val (previousYear, previousDayOfYear) = calendar.run {
            time = previousMessage.getCreatedAtOrThrow()
            get(Calendar.YEAR) to get(Calendar.DAY_OF_YEAR)
        }
        val (year, dayOfYear) = calendar.run {
            time = message.getCreatedAtOrThrow()
            get(Calendar.YEAR) to get(Calendar.DAY_OF_YEAR)
        }
        return previousYear != year || previousDayOfYear != dayOfYear
    }

    private fun Result<Flag>.isAlreadyExistsError(): Boolean {
        if (!isError) {
            return false
        }
        val chatError = error() as ChatNetworkError
        return chatError.streamCode == 4
    }
}
