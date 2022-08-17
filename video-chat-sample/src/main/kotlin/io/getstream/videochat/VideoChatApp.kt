package io.getstream.videochat

import android.app.Application
import io.getstream.chat.android.client.ChatClient
import io.getstream.chat.android.client.logger.ChatLogLevel

class VideoChatApp : Application() {

    override fun onCreate() {
        super.onCreate()

        val client = ChatClient.Builder("sfgpnf7xhf2r", applicationContext)
            .logLevel(ChatLogLevel.ALL)
            .build()

        val user = SampleData.createUser()
        client.connectUser(
            user = user,
            token = user.token
        ).enqueue()
    }
}
