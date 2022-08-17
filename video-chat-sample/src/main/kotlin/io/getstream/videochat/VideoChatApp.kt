package io.getstream.videochat

import android.app.Application
import io.getstream.chat.android.client.ChatClient
import io.getstream.chat.android.client.logger.ChatLogLevel
import io.getstream.chat.android.client.models.User

class VideoChatApp : Application() {

    override fun onCreate() {
        super.onCreate()

        val client = ChatClient.Builder("sfgpnf7xhf2r", applicationContext)
            .logLevel(ChatLogLevel.ALL)
            .build()

        val userCredentials = SampleData.createUsers().random()
        client.connectUser(
            user = User(
                id = userCredentials.id,
                name = userCredentials.name,
                image = userCredentials.image
            ),
            token = userCredentials.token
        ).enqueue()
    }
}
