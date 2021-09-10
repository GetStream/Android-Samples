package io.getstream.chat.virtualevent

import android.app.Application
import io.getstream.chat.android.client.ChatClient
import io.getstream.chat.android.client.logger.ChatLogLevel
import io.getstream.chat.android.livedata.ChatDomain

class VirtualEventApp : Application() {

    override fun onCreate() {
        super.onCreate()

        val client = ChatClient.Builder(AppConfig.API_KEY, applicationContext)
            .logLevel(ChatLogLevel.ALL)
            .build()
        ChatDomain.Builder(client, applicationContext).build()

        client.connectUser(
            user = AppConfig.user,
            token = AppConfig.USER_TOKEN
        ).enqueue()
    }
}
