package io.getstream.whatsappclone.ui.home

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import io.getstream.whatsappclone.R

class HomePageFragment : Fragment(R.layout.fragment_home_page) {

    private val name: String by lazy { requireArguments().getString(ARG_NAME)!! }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        view.findViewById<TextView>(R.id.textView).text = name
    }

    companion object {
        private const val ARG_NAME = "name"

        fun newInstance(name: String): HomePageFragment {
            return HomePageFragment().apply {
                arguments = bundleOf(ARG_NAME to name)
            }
        }
    }
}
