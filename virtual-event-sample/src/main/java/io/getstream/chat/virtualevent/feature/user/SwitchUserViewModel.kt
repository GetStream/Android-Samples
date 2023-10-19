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

package io.getstream.chat.virtualevent.feature.user

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.getstream.chat.android.client.ChatClient
import io.getstream.chat.android.models.User
import io.getstream.chat.virtualevent.AppConfig
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SwitchUserViewModel : ViewModel() {

    private val _state: MutableLiveData<State> = MutableLiveData()
    private val _events: MutableLiveData<UiEvent> = MutableLiveData()
    val state: LiveData<State> = _state
    val events: LiveData<UiEvent> = _events

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
                    _events.postValue(UiEvent.NavigateToHomeScreen)
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
