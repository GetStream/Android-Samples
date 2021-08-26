package io.getstream.chat.virtualevent.feature.events

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class EventsViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is Events Fragment"
    }
    val text: LiveData<String> = _text
}
