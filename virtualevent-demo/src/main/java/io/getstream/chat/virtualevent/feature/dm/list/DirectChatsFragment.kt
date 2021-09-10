package io.getstream.chat.virtualevent.feature.dm.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import io.getstream.chat.android.ui.channel.list.viewmodel.ChannelListViewModel
import io.getstream.chat.android.ui.channel.list.viewmodel.bindView
import io.getstream.chat.android.ui.channel.list.viewmodel.factory.ChannelListViewModelFactory
import io.getstream.chat.virtualevent.databinding.FragmentDirectChatsBinding
import io.getstream.chat.virtualevent.databinding.ViewDmEmptyBinding
import io.getstream.chat.virtualevent.feature.dm.DirectChatActivity
import io.getstream.chat.virtualevent.feature.dm.start.StartDirectChatActivity

/**
 * Fragment that shows a list of direct (1-to-1) conversations.
 */
class DirectChatsFragment : Fragment() {

    private val channelListViewModel: ChannelListViewModel by viewModels {
        ChannelListViewModelFactory()
    }

    private var _binding: FragmentDirectChatsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return FragmentDirectChatsBinding.inflate(inflater, container, false)
            .apply { _binding = this }
            .root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        channelListViewModel.bindView(binding.channelListView, viewLifecycleOwner)

        binding.channelListView.setEmptyStateView(
            ViewDmEmptyBinding.inflate(layoutInflater).root,
            FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.MATCH_PARENT,
                FrameLayout.LayoutParams.MATCH_PARENT,
            )
        )
        binding.channelListView.setChannelItemClickListener { channel ->
            startActivity(DirectChatActivity.createIntent(requireContext(), channel.cid))
        }
        binding.startChatButton.setOnClickListener {
            startActivity(StartDirectChatActivity.createIntent(requireContext()))
        }
        binding.channelListView.setViewHolderFactory(DirectChatListItemVhFactory())
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
