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

 *  The above copyright notice and this permission notice shall be included in
 *  all copies or substantial portions of the Software.

 *  THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 *   IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 *  FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 *  AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 *  LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 *  OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 *  THE SOFTWARE.
 */

package io.getstream.videochat.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import io.getstream.chat.android.client.ChatClient
import io.getstream.chat.android.client.api.models.QueryChannelRequest
import io.getstream.chat.android.client.channel.ChannelClient
import io.getstream.chat.android.client.channel.subscribeFor
import io.getstream.chat.android.client.events.NewMessageEvent
import io.getstream.chat.android.client.models.Message
import io.getstream.chat.android.client.utils.observable.Disposable
import io.getstream.videochat.YoutubeVideo

class VideoDetailViewModel(
    video: YoutubeVideo,
    chatClient: ChatClient = ChatClient.instance()
) : ViewModel() {

    private var subscription: Disposable? = null

    private var channelClient: ChannelClient = chatClient.channel("livestream", video.id)

    private val _messages: MutableLiveData<List<Message>> = MutableLiveData()

    val messages: LiveData<List<Message>> = _messages

    init {
        val query = QueryChannelRequest()
            .withMessages(30)
            .withWatch()
        channelClient.query(query).enqueue {
            if (it.isSuccess) {
                _messages.value = it.data().messages
            }
        }

        channelClient.subscribeFor<NewMessageEvent> { event ->
            _messages.value = (_messages.value ?: emptyList()) + listOf(event.message)
        }
    }

    fun sendMessage(text: String) {
        channelClient.sendMessage(Message(text = text)).enqueue()
    }

    override fun onCleared() {
        subscription?.dispose()
    }
}

class VideoDetailViewModelFactory(
    private val youtubeVideo: YoutubeVideo
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return VideoDetailViewModel(video = youtubeVideo) as T
    }
}
