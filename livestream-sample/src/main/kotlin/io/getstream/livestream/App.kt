package io.getstream.livestream

import android.app.Application
import io.getstream.chat.android.client.ChatClient
import timber.log.Timber
import timber.log.Timber.DebugTree

class App : Application() {
    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {
            Timber.plant(DebugTree())
        }

        ChatClient.Builder(API_KEY, this).build()
    }

    companion object {
        private const val API_KEY = "qx5us2v6xvmh"
    }
}
