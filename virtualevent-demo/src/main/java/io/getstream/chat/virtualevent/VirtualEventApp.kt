package io.getstream.chat.virtualevent

import android.app.Application
import io.getstream.chat.android.client.ChatClient
import io.getstream.chat.android.client.logger.ChatLogLevel
import io.getstream.chat.android.client.models.User
import io.getstream.chat.android.offline.plugin.configuration.Config
import io.getstream.chat.android.offline.plugin.factory.StreamOfflinePluginFactory

class VirtualEventApp : Application() {

    override fun onCreate() {
        super.onCreate()
        setupStreamSdk()
        connectUser()
    }

    private fun setupStreamSdk() {
        val offlinePluginFactory = StreamOfflinePluginFactory(
            config = Config(),
            appContext = this
        )

        ChatClient.Builder(AppConfig.API_KEY, applicationContext)
            .withPlugin(offlinePluginFactory)
            .logLevel(ChatLogLevel.ALL)
            .build()
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
