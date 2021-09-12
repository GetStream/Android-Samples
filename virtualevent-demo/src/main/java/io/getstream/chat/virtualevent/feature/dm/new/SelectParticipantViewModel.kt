package io.getstream.chat.virtualevent.feature.dm.new

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
import io.getstream.chat.virtualevent.util.currentUserId
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * ViewModel responsible for loading and showing available users.
 *
 * **Note**: For simplicity, pagination is not implemented in this ViewModel.
 * Only the first page of available users is loaded.
 */
class SelectParticipantViewModel(
    private val chatClient: ChatClient = ChatClient.instance(),
) : ViewModel() {

    private val _state: MutableLiveData<State> = MutableLiveData()
    private val _events: MutableLiveData<Event<UiEvent>> = MutableLiveData()
    val state: LiveData<State> = _state
    val events: LiveData<Event<UiEvent>> = _events

    init {
        _state.postValue(State.Loading)
        viewModelScope.launch {
            val result = chatClient.queryUsers(
                QueryUsersRequest(
                    filter = Filters.ne("id", currentUserId()),
                    offset = 0,
                    limit = 30,
                )
            ).await()
            if (result.isSuccess) {
                _state.postValue(State.Content(result.data()))
            } else {
                _state.postValue(State.Error(result.error()))
            }
        }
    }

    fun onUserSelected(user: User) {
        viewModelScope.launch(Dispatchers.IO) {
            val result = chatClient.createChannel(
                channelType = "messaging",
                members = listOf(user.id, currentUserId()),
                extraData = mapOf()
            ).await()
            if (result.isSuccess) {
                val cid = result.data().cid
                _events.postValue(Event(UiEvent.NavigateToChat(cid)))
            }
        }
    }

    sealed class State {
        object Loading : State()
        data class Content(val users: List<User>) : State()
        data class Error(val error: ChatError) : State()
    }

    sealed class UiEvent {
        data class NavigateToChat(val cid: String) : UiEvent()
    }
}
