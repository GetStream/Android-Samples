package io.getstream.whatsappclone.ui.channel_list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import io.getstream.chat.android.client.ChatClient
import io.getstream.chat.android.client.models.User
import io.getstream.chat.android.ui.channel.list.viewmodel.ChannelListViewModel
import io.getstream.chat.android.ui.channel.list.viewmodel.bindView
import io.getstream.chat.android.ui.channel.list.viewmodel.factory.ChannelListViewModelFactory
import io.getstream.whatsappclone.databinding.FragmentChannelListBinding
import io.getstream.whatsappclone.ui.home.HomeFragmentDirections

class ChannelListFragment : Fragment() {

    private val viewModel: ChannelListViewModel by viewModels { ChannelListViewModelFactory() }

    private var _binding: FragmentChannelListBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val user = User(id = "jc").apply {
            name = "Jc Miñarro"
            image = "https://ca.slack-edge.com/T02RM6X6B-U011KEXDPB2-891dbb8df64f-128"
        }

        ChatClient.instance()
            .connectUser(
                user = user,
                token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyX2lkIjoiamMifQ.2_5Hae3LKjVSfA0gQxXlZn54Bq6xDlhjPx2J7azUNB4"
            )
            .enqueue()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentChannelListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.bindView(binding.channelList, viewLifecycleOwner)

        binding.channelList.setChannelItemClickListener { channel ->
            findNavController().navigate(HomeFragmentDirections.navHomeToChannel(channel.cid))
        }
    }
}
