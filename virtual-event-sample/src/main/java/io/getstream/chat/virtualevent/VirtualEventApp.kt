/*
 * Copyright 2022 Stream.IO, Inc. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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
