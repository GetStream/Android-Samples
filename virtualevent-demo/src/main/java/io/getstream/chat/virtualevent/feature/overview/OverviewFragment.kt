package io.getstream.chat.virtualevent.feature.overview

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import io.getstream.chat.virtualevent.databinding.FragmentOverviewBinding
import io.getstream.chat.virtualevent.feature.event.EventDetailsActivity

class OverviewFragment : Fragment() {

    private var _binding: FragmentOverviewBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentOverviewBinding.inflate(inflater, container, false)
        val root: View = binding.root
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        this._binding?.event1?.setOnClickListener {
            openEventDetails()
        }

        this._binding?.event2?.setOnClickListener {
            openEventDetails()
        }
    }

    private fun openEventDetails() {
        context?.let {
            startActivity(Intent(it, EventDetailsActivity::class.java))
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
