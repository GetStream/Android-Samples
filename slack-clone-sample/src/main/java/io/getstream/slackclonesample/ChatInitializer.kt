package io.getstream.slackclonesample

import android.content.Context
import io.getstream.chat.android.client.ChatClient
import io.getstream.chat.android.client.logger.ChatLogLevel
import io.getstream.chat.android.livedata.ChatDomain

class ChatInitializer(private val context: Context) {

    @Suppress("UNUSED_VARIABLE")
    fun init(apiKey: String) {
        val logLevel = if (BuildConfig.DEBUG) ChatLogLevel.ALL else ChatLogLevel.NOTHING
        val client = ChatClient.Builder(apiKey, context)
            .logLevel(logLevel)
            .build()

        val domain = ChatDomain.Builder(client, context)
            .userPresenceEnabled()
            .offlineEnabled()
            .build()
    }
}
