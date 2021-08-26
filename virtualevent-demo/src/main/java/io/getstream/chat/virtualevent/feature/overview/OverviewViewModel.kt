package io.getstream.chat.virtualevent.feature.overview

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class OverviewViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is Overview Fragment"
    }
    val text: LiveData<String> = _text
}
