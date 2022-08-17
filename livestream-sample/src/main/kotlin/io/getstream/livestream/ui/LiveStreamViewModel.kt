package io.getstream.livestream.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.getstream.chat.android.client.ChatClient
import io.getstream.chat.android.client.api.models.QueryChannelRequest
import io.getstream.chat.android.client.channel.ChannelClient
import io.getstream.chat.android.client.events.NewMessageEvent
import io.getstream.chat.android.client.models.Message
import io.getstream.chat.android.client.subscribeFor
import timber.log.Timber

class LiveStreamViewModel(
    private val chatClient: ChatClient = ChatClient.instance()
) : ViewModel() {
    private val _viewState = MutableLiveData<State>()

    private lateinit var channelClient: ChannelClient

    val viewState: LiveData<State> = _viewState

    init {
        channelClient = chatClient.channel(CHANNEL_TYPE, CHANNEL_ID)
        requestChannel()
        subscribeToNewMessageEvent()
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
    }
}

sealed class State {
    data class Messages(val messages: List<Message>) : State()
    data class NewMessage(val message: Message) : State()
    data class Error(val message: String) : State()
}
