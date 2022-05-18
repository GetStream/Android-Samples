package io.getstream.chat.android.compose.customattachments

import android.app.Application
import io.getstream.chat.android.client.ChatClient
import io.getstream.chat.android.client.logger.ChatLogLevel
import io.getstream.chat.android.client.models.User
import io.getstream.chat.android.offline.model.message.attachments.UploadAttachmentsNetworkType
import io.getstream.chat.android.offline.plugin.configuration.Config
import io.getstream.chat.android.offline.plugin.factory.StreamOfflinePluginFactory

class CustomAttachmentsApp : Application() {
    override fun onCreate() {
        super.onCreate()
        setupStreamSdk()
        connectUser()
    }

    private fun setupStreamSdk() {
        val offlinePluginFactory = StreamOfflinePluginFactory(
            config = Config(
                backgroundSyncEnabled = true,
                userPresence = true,
                persistenceEnabled = true,
                uploadAttachmentsNetworkType = UploadAttachmentsNetworkType.NOT_ROAMING,
            ),
            appContext = applicationContext,
        )
        ChatClient.Builder("qx5us2v6xvmh", applicationContext)
            .logLevel(ChatLogLevel.ALL)
            .withPlugin(offlinePluginFactory)
            .build()
    }

    private fun connectUser() {
        ChatClient.instance().connectUser(
            user = User(
                id = "filip",
                extraData = mutableMapOf(
                    "name" to "Filip Babić",
                    "image" to "https://ca.slack-edge.com/T02RM6X6B-U022AFX9D2S-f7bcb3d56180-128",
                ),
            ),
            token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyX2lkIjoiZmlsaXAifQ.WKqTjU6fHHjtFej-sUqS2ml3Rvdqn4Ptrf7jfKqzFgU"
        ).enqueue()
    }
}
