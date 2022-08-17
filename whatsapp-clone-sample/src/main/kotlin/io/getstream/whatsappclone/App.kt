package io.getstream.whatsappclone

import android.app.Application
import io.getstream.chat.android.client.ChatClient
import io.getstream.chat.android.offline.plugin.configuration.Config
import io.getstream.chat.android.offline.plugin.factory.StreamOfflinePluginFactory

class App : Application() {

    override fun onCreate() {
        super.onCreate()

        val offlinePluginFactory = StreamOfflinePluginFactory(
            config = Config(),
            appContext = this
        )

        ChatClient.Builder(API_KEY, this)
            .withPlugin(offlinePluginFactory)
            .build()
    }

    companion object {
        private const val API_KEY = "qx5us2v6xvmh"
    }
}
