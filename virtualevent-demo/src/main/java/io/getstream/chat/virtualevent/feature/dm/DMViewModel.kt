package io.getstream.chat.virtualevent.feature.dm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class DMViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is DM Fragment"
    }
    val text: LiveData<String> = _text
}
