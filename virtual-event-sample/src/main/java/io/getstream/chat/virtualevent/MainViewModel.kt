package io.getstream.chat.virtualevent

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import io.getstream.chat.android.client.ChatClient
import io.getstream.chat.android.client.models.User
import io.getstream.chat.android.client.setup.state.ClientState

class MainViewModel(
    clientState: ClientState = ChatClient.instance().clientState,
) : ViewModel() {

    val currentUser: LiveData<User?> = clientState.user.asLiveData()
}
