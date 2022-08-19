package io.getstream.whatsappclone

import android.app.Application
import io.getstream.chat.android.client.ChatClient
import io.getstream.chat.android.client.models.User
import io.getstream.chat.android.offline.plugin.configuration.Config
import io.getstream.chat.android.offline.plugin.factory.StreamOfflinePluginFactory

class WhatsApp : Application() {

    override fun onCreate() {
        super.onCreate()

        val offlinePluginFactory = StreamOfflinePluginFactory(
            config = Config(),
            appContext = this
        )
        ChatClient.Builder("qx5us2v6xvmh", this)
            .withPlugin(offlinePluginFactory)
            .build()

        val user = User().apply {
            id = "jc"
            name = "Jc Miñarro"
            image = "https://ca.slack-edge.com/T02RM6X6B-U011KEXDPB2-891dbb8df64f-128"
        }
        ChatClient.instance()
            .connectUser(
                user = user,
                token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyX2lkIjoiamMifQ.2_5Hae3LKjVSfA0gQxXlZn54Bq6xDlhjPx2J7azUNB4"
            )
            .enqueue()
    }
}
