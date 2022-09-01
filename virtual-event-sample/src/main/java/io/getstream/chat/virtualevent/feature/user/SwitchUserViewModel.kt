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

package io.getstream.chat.virtualevent.feature.user

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.getstream.chat.android.client.ChatClient
import io.getstream.chat.android.client.models.User
import io.getstream.chat.android.livedata.utils.Event
import io.getstream.chat.virtualevent.AppConfig
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SwitchUserViewModel : ViewModel() {

    private val _state: MutableLiveData<State> = MutableLiveData()
    private val _events: MutableLiveData<Event<UiEvent>> = MutableLiveData()
    val state: LiveData<State> = _state
    val events: LiveData<Event<UiEvent>> = _events

    init {
        val users = AppConfig.availableUsers
            .map { (id, name, _, image) ->
                User(
                    id = id,
                    name = name,
                    image = image,
                )
            }
        _state.postValue(State.Content(users))
    }

    fun onUserSelected(user: User) {
        ChatClient.instance().disconnect(true).enqueue()

        // TODO: check why this doesn't work without a delay
        viewModelScope.launch {
            delay(300)
            connectUser(user)
        }
    }

    private fun connectUser(user: User) {
        val token = AppConfig.availableUsers
            .first { it.id == user.id }
            .token
        ChatClient.instance()
            .connectUser(user, token)
            .enqueue { result ->
                if (result.isSuccess) {
                    _events.postValue(Event(UiEvent.NavigateToHomeScreen))
                }
            }
    }

    sealed class State {
        data class Content(val users: List<User>) : State()
    }

    sealed class UiEvent {
        object NavigateToHomeScreen : UiEvent()
    }
}
