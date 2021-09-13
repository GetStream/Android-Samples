package io.getstream.chat.virtualevent

import android.app.Application
import io.getstream.chat.android.client.ChatClient
import io.getstream.chat.android.client.logger.ChatLogLevel
import io.getstream.chat.android.client.models.User
import io.getstream.chat.android.livedata.ChatDomain

class VirtualEventApp : Application() {

    override fun onCreate() {
        super.onCreate()
        setupStreamSdk()
        connectUser()
    }

    private fun setupStreamSdk() {
        val client = ChatClient.Builder(AppConfig.API_KEY, applicationContext)
            .logLevel(ChatLogLevel.ALL)
            .build()
        ChatDomain.Builder(client, applicationContext).build()
    }

    private fun connectUser() {
        val userCredentials = AppConfig.availableUsers[0]
        ChatClient.instance().connectUser(
            user = User(
                id = userCredentials.id,
                extraData = mutableMapOf(
                    "name" to userCredentials.name,
                    "image" to userCredentials.image
                )
            ),
            token = userCredentials.token
        ).enqueue()
    }
}
