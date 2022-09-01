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

package io.getstream.chat.android.compose.customattachments.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import io.getstream.chat.android.compose.customattachments.ui.CustomMessagesScreen
import io.getstream.chat.android.compose.customattachments.ui.dateAttachmentFactory
import io.getstream.chat.android.compose.customattachments.ui.quotedDateAttachmentFactory
import io.getstream.chat.android.compose.ui.attachments.StreamAttachmentFactories
import io.getstream.chat.android.compose.ui.theme.ChatTheme

class MessagesActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val channelId = requireNotNull(intent.getStringExtra(KEY_CHANNEL_ID))

        val customFactories = listOf(dateAttachmentFactory)
        val defaultFactories = StreamAttachmentFactories.defaultFactories()

        val customQuotedFactories = listOf(quotedDateAttachmentFactory)
        val defaultQuotedFactories = StreamAttachmentFactories.defaultQuotedFactories()

        setContent {
            ChatTheme(
                attachmentFactories = customFactories + defaultFactories,
                quotedAttachmentFactories = customQuotedFactories + defaultQuotedFactories
            ) {
                CustomMessagesScreen(
                    channelId = channelId,
                    onBackPressed = { finish() }
                )
            }
        }
    }

    companion object {
        private const val KEY_CHANNEL_ID = "channelId"

        fun getIntent(context: Context, channelId: String): Intent {
            return Intent(context, MessagesActivity::class.java).apply {
                putExtra(KEY_CHANNEL_ID, channelId)
            }
        }
    }
}
