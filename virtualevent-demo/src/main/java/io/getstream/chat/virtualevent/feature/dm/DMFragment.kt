package io.getstream.chat.virtualevent.feature.dm

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import io.getstream.chat.android.ui.channel.list.adapter.viewholder.ChannelListItemViewHolderFactory
import io.getstream.chat.android.ui.channel.list.viewmodel.ChannelListViewModel
import io.getstream.chat.android.ui.channel.list.viewmodel.bindView
import io.getstream.chat.android.ui.channel.list.viewmodel.factory.ChannelListViewModelFactory
import io.getstream.chat.virtualevent.databinding.FragmentDmBinding
import io.getstream.chat.virtualevent.databinding.ViewEmptyDirectMessagesBinding

class DMFragment : Fragment() {
    private val dmViewModel: DMViewModel by viewModels()
    private val channelListViewModel: ChannelListViewModel by viewModels {
        ChannelListViewModelFactory()
    }

    private var _binding: FragmentDmBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDmBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        channelListViewModel.bindView(binding.channelListView, viewLifecycleOwner)

        binding.channelListView.setChannelItemClickListener { channel ->
            // TODO: navigate to participant selection screen
        }
        // TODO: implement custom channel item ViewHolder
        binding.channelListView.setViewHolderFactory(ChannelListItemViewHolderFactory())

        setupEmptyView()
        setupStartChatButton()
    }

    private fun setupEmptyView() {
        val emptyView = ViewEmptyDirectMessagesBinding.inflate(layoutInflater).root
        val layoutParams = FrameLayout.LayoutParams(
            FrameLayout.LayoutParams.MATCH_PARENT,
            FrameLayout.LayoutParams.MATCH_PARENT,
        )
        binding.channelListView.setEmptyStateView(emptyView, layoutParams)
    }

    private fun setupStartChatButton() {
        binding.startChatButton.setOnClickListener {
            // TODO: navigate to participant selection screen
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
