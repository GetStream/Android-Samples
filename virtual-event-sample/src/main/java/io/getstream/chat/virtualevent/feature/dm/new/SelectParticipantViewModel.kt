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

package io.getstream.chat.virtualevent.feature.dm.new

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.getstream.chat.android.client.ChatClient
import io.getstream.chat.android.client.api.models.QueryUsersRequest
import io.getstream.chat.android.client.errors.ChatError
import io.getstream.chat.android.client.models.Filters
import io.getstream.chat.android.client.models.User
import io.getstream.chat.android.livedata.utils.Event
import io.getstream.chat.virtualevent.util.currentUserId
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
                    filter = Filters.and(
                        Filters.ne("id", currentUserId()),
                        Filters.ne("role", "admin"),
                    ),
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
        viewModelScope.launch {
            val result = chatClient.createChannel(
                channelType = "messaging",
                channelId = "",
                memberIds = listOf(user.id, currentUserId()),
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
        data class Content(val participants: List<User>) : State()
        data class Error(val error: ChatError) : State()
    }

    sealed class UiEvent {
        data class NavigateToChat(val cid: String) : UiEvent()
    }
}
