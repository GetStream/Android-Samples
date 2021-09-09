package io.getstream.chat.virtualevent.feature.dm

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import io.getstream.chat.android.livedata.utils.EventObserver
import io.getstream.chat.virtualevent.R
import io.getstream.chat.virtualevent.databinding.FragmentSelectUserBinding

class SelectUserFragment : Fragment() {

    private var _binding: FragmentSelectUserBinding? = null
    private val binding get() = _binding!!

    private val viewModel: SelectUserViewModel by viewModels()

    private val adapter: UserListAdapter = UserListAdapter {
        viewModel.onAction(SelectUserViewModel.Action.UserClicked(it))
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSelectUserBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.usersRecyclerView.adapter = adapter
        with(viewModel) {
            usersState.observe(viewLifecycleOwner, ::renderUsersState)
            navigationEvents.observe(viewLifecycleOwner, EventObserver(::handleNavigationEvent))
        }
    }

    private fun renderUsersState(state: SelectUserViewModel.State) {
        when (state) {
            is SelectUserViewModel.State.Content -> adapter.setUsers(state.users)
        }
    }

    private fun handleNavigationEvent(event: SelectUserViewModel.NavigationEvent) {
        when (event) {
            is SelectUserViewModel.NavigationEvent.RedirectToChat -> requireActivity()
                .findNavController(R.id.hostFragmentContainer)
                .navigate(SelectUserFragmentDirections.actionToMessageListFragment(event.cid))
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
