package io.getstream.chat.virtualevent.feature.overview

import android.os.Bundle
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import io.getstream.chat.virtualevent.AppConfig
import io.getstream.chat.virtualevent.R
import io.getstream.chat.virtualevent.databinding.FragmentOverviewBinding
import io.getstream.chat.virtualevent.feature.event.detail.EventDetailsActivity

/**
 * Fragment that shows information about the conference: title, description,
 * statistics, partners, schedule, etc.
 */
class OverviewFragment : Fragment() {

    private var _binding: FragmentOverviewBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return FragmentOverviewBinding.inflate(inflater, container, false)
            .apply { _binding = this }
            .root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.titleSectionInclude.eventSubtitleTextView.text = Html.fromHtml(
            getString(R.string.overview_subtitle),
            Html.FROM_HTML_MODE_COMPACT
        )
        binding.event1Include.event1CardView.setOnClickListener {
            startActivity(
                EventDetailsActivity.createIntent(
                    requireContext(),
                    AppConfig.LIVESTREAM_ESG_DATA,
                    requireContext().getString(R.string.overview_hypercube_title)
                )
            )
        }
        binding.event2Include.event2CardView.setOnClickListener {
            startActivity(
                EventDetailsActivity.createIntent(
                    requireContext(),
                    AppConfig.LIVESTREAM_DATA_STRATEGY,
                    requireContext().getString(R.string.overview_layers_title)
                )
            )
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
