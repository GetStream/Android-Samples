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
            id = "aditlal",
            extraData = mutableMapOf(
                "name" to "Adit Lal",
                "image" to "https://firebasestorage.googleapis.com/v0/b/stream-chat-internal.appspot.com/o/users%2FJc.png?alt=media",
            ),
        )

        client.connectUser(
            user = user,
            token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyX2lkIjoiYWRpdGxhbCJ9.Q8oM9czJlsGV5RiV2pEF-y9XnwUh--5XbF1j7Dc861U"
        ).enqueue()
    }

    companion object {
        private const val API_KEY = "qx5us2v6xvmh"
    }
}
