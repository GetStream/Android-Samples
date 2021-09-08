package io.getstream.chat.virtualevent

import android.app.Application
import io.getstream.chat.android.client.ChatClient
import io.getstream.chat.android.client.logger.ChatLogLevel
import io.getstream.chat.android.client.models.User
import io.getstream.chat.android.livedata.ChatDomain

class VirtualEventApp : Application() {

    override fun onCreate() {
        super.onCreate()

        val client = ChatClient.Builder("js6fh2y78g6w", applicationContext)
            .logLevel(ChatLogLevel.ALL)
            .build()
        ChatDomain.Builder(client, applicationContext).build()

        val user = User(
            id = "samuel_urbanowicz",
            extraData = mutableMapOf(
                "name" to "Samuel",
                "image" to "https://firebasestorage.googleapis.com/v0/b/stream-chat-internal.appspot.com/o/users%2FJc.png?alt=media",
            ),
        )

        client.connectUser(
            user = user,
            token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyX2lkIjoic2FtdWVsX3VyYmFub3dpY3oifQ.Zsn_9f5LbvyV9F5jQ-_h7YvQNnNsnUQ6_IMRMemA334"
        ).enqueue()
    }
}
