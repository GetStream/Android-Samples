package io.getstream.chat.virtualevent.feature.overview

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import io.getstream.chat.virtualevent.databinding.FragmentOverviewBinding

class OverviewFragment : Fragment() {

    private lateinit var overviewViewModel: OverviewViewModel
    private var _binding: FragmentOverviewBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        overviewViewModel = ViewModelProvider(this).get(OverviewViewModel::class.java)

        _binding = FragmentOverviewBinding.inflate(inflater, container, false)
        val root: View = binding.root

        overviewViewModel.text.observe(
            viewLifecycleOwner,
            Observer {
            }
        )
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
