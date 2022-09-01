/*
 * Copyright 2022 Stream.IO, Inc. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.getstream.chat.virtualevent.feature.event.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import io.getstream.chat.virtualevent.AppConfig
import io.getstream.chat.virtualevent.R
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
}
