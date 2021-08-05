package io.getstream.livestream.compose

import android.app.Application
import androidx.camera.camera2.Camera2Config
import androidx.camera.core.CameraXConfig
import io.getstream.chat.android.client.ChatClient
import io.getstream.chat.android.client.models.User
import io.getstream.chat.android.livedata.ChatDomain
import timber.log.Timber

class App : Application(), CameraXConfig.Provider {
    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }

        val client = ChatClient.Builder("kumvbr5ah5jg", applicationContext)
            .build()
        ChatDomain.Builder(client, applicationContext).build()

        val user = User(
            id = "filip-b",
            extraData = mutableMapOf(
                "name" to "Filip B",
                "image" to "https://firebasestorage.googleapis.com/v0/b/stream-chat-internal.appspot.com/o/users%2FJc.png?alt=media",
            ),
        )

        client.connectUser(
            user = user,
            token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyX2lkIjoiZmlsaXAtYiJ9.MrBdSmiRFB1CRzWFXprjcXPo86W4H2N82KRgJVCXt60"
        ).enqueue()
    }

    override fun getCameraXConfig(): CameraXConfig {
        return Camera2Config.defaultConfig()
    }
}
