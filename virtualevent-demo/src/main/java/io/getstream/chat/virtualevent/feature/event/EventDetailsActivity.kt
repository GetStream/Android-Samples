package io.getstream.chat.virtualevent.feature.event

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import io.getstream.chat.virtualevent.R

class EventDetailsActivity : AppCompatActivity(R.layout.activity_event_details) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
        }
    }
}