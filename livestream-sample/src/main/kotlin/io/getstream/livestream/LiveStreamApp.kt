package io.getstream.livestream

import android.app.Application
import io.getstream.chat.android.client.ChatClient
import io.getstream.chat.android.client.logger.ChatLogLevel
import io.getstream.chat.android.client.models.User
import timber.log.Timber
import timber.log.Timber.DebugTree

class LiveStreamApp : Application() {

    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {
            Timber.plant(DebugTree())
        }

        val client = ChatClient.Builder("qx5us2v6xvmh", applicationContext)
            .logLevel(ChatLogLevel.ALL)
            .build()

        val user = User(
            id = "jc",
            name = "Jc Miñarro",
            image = "https://ca.slack-edge.com/T02RM6X6B-U011KEXDPB2-891dbb8df64f-128"
        )
        client.connectUser(
            user = user,
            token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyX2lkIjoiamMifQ.2_5Hae3LKjVSfA0gQxXlZn54Bq6xDlhjPx2J7azUNB4"
        ).enqueue()
    }
}
