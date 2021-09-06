package io.getstream.chat.virtualevent

import android.app.Application
import io.getstream.chat.android.client.ChatClient
import io.getstream.chat.android.client.logger.ChatLogLevel
import io.getstream.chat.android.client.models.User
import io.getstream.chat.android.livedata.ChatDomain

class VirtualEventApp : Application() {

    override fun onCreate() {
        super.onCreate()

        val client = ChatClient.Builder("bfsgvmwp9gqx", applicationContext)
            .logLevel(ChatLogLevel.ALL)
            .build()
        ChatDomain.Builder(client, applicationContext).build()

        val user = User(
            id = "5531a8cb-3b81-4a54-b424-7ae4e27bf8ba",
            extraData = mutableMapOf(
                "name" to "Samuel",
                "image" to "https://firebasestorage.googleapis.com/v0/b/stream-chat-internal.appspot.com/o/users%2FJc.png?alt=media",
            ),
        )

        client.connectUser(
            user = user,
            token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1c2VyX2lkIjoiNTUzMWE4Y2ItM2I4MS00YTU0LWI0MjQtN2FlNGUyN2JmOGJhIn0.PXkmukg3JU4igH_YUMr7WC7a1EcwKBr_C5V2ouBlmIs"
        ).enqueue()
    }
}
