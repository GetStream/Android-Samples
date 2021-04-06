package io.getstream.whatsappclone

import android.app.Application
import io.getstream.chat.android.client.ChatClient
import io.getstream.chat.android.livedata.ChatDomain

class App : Application() {

    override fun onCreate() {
        super.onCreate()

        val client = ChatClient.Builder(API_KEY, this)
            .build()

        ChatDomain.Builder(client, this).build()
    }

    companion object {
        private const val API_KEY = "qx5us2v6xvmh"
    }
}
