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
        val offlinePluginFactory = StreamOfflinePluginFactory(
            config = Config(),
            appContext = this
        )
        ChatClient.Builder("7de6n9dsyzjx", applicationContext)
            .withPlugin(offlinePluginFactory)
            .logLevel(ChatLogLevel.ALL)
            .build()

        val userCredentials = AppConfig.availableUsers[0]
        val user = User(
            id = userCredentials.id,
            extraData = mutableMapOf(
                "name" to userCredentials.name,
                "image" to userCredentials.image
            )
        )
        ChatClient.instance().connectUser(
            user = user,
            token = userCredentials.token
        ).enqueue()
    }
}
