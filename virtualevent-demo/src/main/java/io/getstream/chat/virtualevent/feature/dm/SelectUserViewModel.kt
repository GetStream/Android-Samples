package io.getstream.chat.virtualevent.feature.dm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.getstream.chat.android.client.ChatClient
import io.getstream.chat.android.client.api.models.QueryUsersRequest
import io.getstream.chat.android.client.call.await
import io.getstream.chat.android.client.errors.ChatError
import io.getstream.chat.android.client.models.Filters
import io.getstream.chat.android.client.models.User
import io.getstream.chat.android.livedata.utils.Event
import io.getstream.chat.android.offline.ChatDomain
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SelectUserViewModel(
    private val chatClient: ChatClient = ChatClient.instance(),
    private val chatDomain: ChatDomain = ChatDomain.instance()
) : ViewModel() {

    private val _usersState: MutableLiveData<State> = MutableLiveData()
    private val _navigationEvents: MutableLiveData<Event<NavigationEvent>> = MutableLiveData()
    val usersState: LiveData<State> = _usersState
    val navigationEvents: LiveData<Event<NavigationEvent>> = _navigationEvents

    init {
        _usersState.postValue(State.Loading)
        viewModelScope.launch {
            val result = chatClient.queryUsers(
                QueryUsersRequest(
                    filter = Filters.ne("id", ChatClient.instance().getCurrentUser()?.id ?: ""),
                    offset = 0,
                    limit = QUERY_LIMIT,
                )
            ).await()
            if (result.isSuccess) {
                _usersState.postValue(State.Content(result.data()))
            } else {
                _usersState.postValue(State.Error(result.error()))
            }
        }
    }

    fun onAction(action: Action) {
        when (action) {
            is Action.UserClicked -> {
                onUserSelected(action.user)
            }
        }
    }

    private fun onUserSelected(user: User) {
        viewModelScope.launch(Dispatchers.IO) {
            val currentUserId =
                chatDomain.user.value?.id ?: error("User must be set before create new channel!")
            val result = chatClient.createChannel(
                channelType = "messaging",
                members = listOf(user.id, currentUserId),
                extraData = mapOf()
            ).await()
            if (result.isSuccess) {
                val cid = result.data().cid
                _navigationEvents.postValue(Event(NavigationEvent.RedirectToChat(cid)))
            }
        }
    }

    sealed class Action {
        data class UserClicked(val user: User) : Action()
    }

    sealed class State {
        object Loading : State()
        data class Content(val users: List<User>) : State()
        data class Error(val chatError: ChatError) : State()
    }

    sealed class NavigationEvent {
        data class RedirectToChat(val cid: String) : NavigationEvent()
    }

    companion object {
        private const val QUERY_LIMIT = 30
    }
}
