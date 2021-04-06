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

        val user = User(
            id = "1f37e58d-d8b0-476a-a4f2-f8611e0d85d9",
            extraData = mutableMapOf(
                "name" to "Tutorial Droid",
                "image" to "https://bit.ly/2TIt8NR",
            ),
        )
        ChatClient.instance()
            .connectUser(
                user = user,
                token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1c2VyX2lkIjoiMWYzN2U1OGQtZDhiMC00NzZhLWE0ZjItZjg2MTFlMGQ4NWQ5In0.l3u9P1NKhJ91rI1tzOcABGh045Kj69-iVkC2yUtohVw"
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
