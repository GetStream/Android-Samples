/*
 *  The MIT License (MIT)
 *
 *  Copyright 2022 Stream.IO, Inc. All Rights Reserved.
 *
 *  Permission is hereby granted, free of charge, to any person obtaining a copy
 *  of this software and associated documentation files (the "Software"), to deal
 *  in the Software without restriction, including without limitation the rights
 *   to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 *   copies of the Software, and to permit persons to whom the Software is
 *  furnished to do so, subject to the following conditions:
 *
 *  The above copyright notice and this permission notice shall be included in
 *  all copies or substantial portions of the Software.
 *
 *  THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 *   IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 *  FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 *  AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 *  LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 *  OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 *  THE SOFTWARE.
 */

package io.getstream.chat.virtualevent.feature.dm.new

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.getstream.chat.android.client.ChatClient
import io.getstream.chat.android.client.api.models.QueryUsersRequest
import io.getstream.chat.android.client.errors.ChatError
import io.getstream.chat.android.client.utils.flatMap
import io.getstream.chat.android.models.Filters
import io.getstream.chat.android.models.User
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
    private val _events: MutableLiveData<UiEvent> = MutableLiveData()
    val state: LiveData<State> = _state
    val events: LiveData<UiEvent> = _events

    init {
        _state.postValue(State.Loading)
        viewModelScope.launch {
            chatClient.queryUsers(
                QueryUsersRequest(
                    filter = Filters.and(
                        Filters.ne("id", currentUserId()),
                        Filters.ne("role", "admin"),
                    ),
                    offset = 0,
                    limit = 30,
                )
            )
                .await()
                .onSuccess { _state.postValue(State.Content(it)) }
                .onError { _state.postValue(State.Error(it)) }
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
                .onSuccess { _events.postValue(UiEvent.NavigateToChat(it.cid)) }
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
