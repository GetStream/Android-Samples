package io.getstream.livestream.compose

import android.app.Application
import io.getstream.chat.android.client.ChatClient
import io.getstream.chat.android.client.models.User
import io.getstream.chat.android.offline.ChatDomain
import timber.log.Timber

class App : Application() {
    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }

        val client = ChatClient.Builder("kumvbr5ah5jg", applicationContext)
            .build()
        ChatDomain.Builder(client, applicationContext).build()

        val user = User(
            id = "1f37e58d-d8b0-476a-a4f2-f8611e0d85d9",
            extraData = mutableMapOf(
                "name" to "Jc",
                "image" to "https://firebasestorage.googleapis.com/v0/b/stream-chat-internal.appspot.com/o/users%2FJc.png?alt=media",
            ),
        )

        client.connectUser(
            user = user,
            token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1c2VyX2lkIjoiMWYzN2U1OGQtZDhiMC00NzZhLWE0ZjItZjg2MTFlMGQ4NWQ5In0.l3u9P1NKhJ91rI1tzOcABGh045Kj69-iVkC2yUtohVw"
        ).enqueue()
    }

    companion object {
        private const val API_KEY = "qx5us2v6xvmh"
    }
}
