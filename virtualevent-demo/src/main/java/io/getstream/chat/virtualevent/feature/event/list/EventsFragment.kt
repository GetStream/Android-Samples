package io.getstream.chat.virtualevent.feature.event.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import io.getstream.chat.virtualevent.AppConfig
import io.getstream.chat.virtualevent.databinding.FragmentEventsBinding
import io.getstream.chat.virtualevent.feature.event.detail.EventDetailsActivity

/**
 * Fragment with a list of conference events.
 */
class EventsFragment : Fragment() {

    private var _binding: FragmentEventsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return FragmentEventsBinding.inflate(inflater, container, false)
            .apply { _binding = this }
            .root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.event1Include.event1CardView.setOnClickListener {
            startActivity(
                EventDetailsActivity.createIntent(
                    requireContext(),
                    AppConfig.LIVESTREAM_CHANNEL_1
                )
            )
        }
        binding.event2Include.event2CardView.setOnClickListener {
            startActivity(
                EventDetailsActivity.createIntent(
                    requireContext(),
                    AppConfig.LIVESTREAM_CHANNEL_2
                )
            )
        }
    }
}
