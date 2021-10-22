package io.getstream.livestream

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.getstream.chat.android.client.ChatClient
import io.getstream.chat.android.client.api.models.QueryChannelRequest
import io.getstream.chat.android.client.channel.ChannelClient
import io.getstream.chat.android.client.events.NewMessageEvent
import io.getstream.chat.android.client.models.Message
import io.getstream.chat.android.client.models.User
import io.getstream.chat.android.client.models.image
import io.getstream.chat.android.client.models.name
import io.getstream.chat.android.client.subscribeFor
import timber.log.Timber

class LiveStreamViewModel : ViewModel() {
    private val chatClient = ChatClient.instance()
    private val _viewState = MutableLiveData<State>()

    private lateinit var channelClient: ChannelClient

    val viewState: LiveData<State> = _viewState

    init {
        chatClient.connectUser(chatUser, USER_TOKEN).enqueue {
            if (it.isSuccess) {
                channelClient = chatClient.channel(CHANNEL_TYPE, CHANNEL_ID)
                requestChannel()
                subscribeToNewMessageEvent()
            } else {
                _viewState.postValue(State.Error("User setting error"))
                Timber.e(it.error().cause)
            }
        }
    }

    fun sendButtonClicked(text: String) {
        channelClient.sendMessage(Message(text = text)).enqueue {
            if (it.isSuccess) {
                Timber.d("Message send success")
            } else {
                _viewState.postValue(State.Error("Messsage sending error"))
                Timber.e(it.error().cause)
            }
        }
    }

    private fun subscribeToNewMessageEvent() {
        chatClient.subscribeFor<NewMessageEvent> {
            _viewState.postValue(State.NewMessage(it.message))
        }
    }

    private fun requestChannel() {
        val request = QueryChannelRequest()
            .withData(mapOf("name" to "Live stream chat"))
            .withMessages(20)
            .withWatch()

        channelClient.query(request).enqueue {
            if (it.isSuccess) {
                _viewState.postValue(State.Messages(it.data().messages))
            } else {
                _viewState.postValue(State.Error("QueryChannelRequest error"))
                Timber.e(it.error().cause)
            }
        }
    }

    companion object {
        private const val CHANNEL_TYPE = "livestream"
        private const val CHANNEL_ID = "livestream-clone-android"

        private val chatUser = User(id = "jc").apply {
            name = "Jc Miñarro"
            image = "https://ca.slack-edge.com/T02RM6X6B-U011KEXDPB2-891dbb8df64f-128"
        }
        private const val USER_TOKEN =
            "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyX2lkIjoiamMifQ.2_5Hae3LKjVSfA0gQxXlZn54Bq6xDlhjPx2J7azUNB4"
    }
}

sealed class State {
    data class Messages(val messages: List<Message>) : State()
    data class NewMessage(val message: Message) : State()
    data class Error(val message: String) : State()
}
