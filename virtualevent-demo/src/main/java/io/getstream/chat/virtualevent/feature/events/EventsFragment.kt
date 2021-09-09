package io.getstream.chat.virtualevent.feature.events

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import io.getstream.chat.virtualevent.databinding.FragmentEventsBinding
import io.getstream.chat.virtualevent.feature.event.EventDetailsActivity

class EventsFragment : Fragment() {

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
        binding.event1.root.setOnClickListener {
            EventDetailsActivity.openActivity(requireContext(), EventDetailsActivity.cid1)
        }
        binding.event2.root.setOnClickListener {
            EventDetailsActivity.openActivity(requireContext(), EventDetailsActivity.cid2)
        }
    }
}
