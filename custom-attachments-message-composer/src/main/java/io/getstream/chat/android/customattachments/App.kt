/*
 *  The MIT License (MIT)
 *
 *  Copyright 2022 Stream.IO, Inc. All Rights Reserved.
 *
 *  Permission is hereby granted, free of charge, to any person obtaining a copy
 *  of this software and associated documentation files (the "Software"), to deal
 *  in the Software without restriction, including without limitation the rights
 *   to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 *   copies of the Software, and to permit persons to whom the Software is
 *  furnished to do so, subject to the following conditions:
 *
 *  The above copyright notice and this permission notice shall be included in
 *  all copies or substantial portions of the Software.
 *
 *  THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 *   IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 *  FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 *  AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 *  LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 *  OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 *  THE SOFTWARE.
 */

package io.getstream.chat.android.customattachments

import android.app.Application
import io.getstream.chat.android.client.ChatClient
import io.getstream.chat.android.client.logger.ChatLogLevel
import io.getstream.chat.android.client.models.User
import io.getstream.chat.android.customattachments.factory.DateAttachmentFactory
import io.getstream.chat.android.customattachments.factory.DateAttachmentPreviewFactory
import io.getstream.chat.android.customattachments.factory.QuotedDateAttachmentFactory
import io.getstream.chat.android.offline.plugin.configuration.Config
import io.getstream.chat.android.offline.plugin.factory.StreamOfflinePluginFactory
import io.getstream.chat.android.ui.ChatUI
import io.getstream.chat.android.ui.message.composer.attachment.AttachmentPreviewFactoryManager
import io.getstream.chat.android.ui.message.composer.attachment.factory.FileAttachmentPreviewFactory
import io.getstream.chat.android.ui.message.composer.attachment.factory.ImageAttachmentPreviewFactory
import io.getstream.chat.android.ui.message.list.adapter.viewholder.attachment.AttachmentFactoryManager
import io.getstream.chat.android.ui.message.list.adapter.viewholder.attachment.DefaultQuotedAttachmentMessageFactory
import io.getstream.chat.android.ui.message.list.adapter.viewholder.attachment.QuotedAttachmentFactoryManager

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        setupStreamSdk()
        connectUser()
    }

    private fun setupStreamSdk() {
        val offlinePluginFactory = StreamOfflinePluginFactory(
            config = Config(),
            appContext = applicationContext
        )
        ChatClient.Builder("qx5us2v6xvmh", applicationContext)
            .logLevel(ChatLogLevel.ALL)
            .withPlugin(offlinePluginFactory)
            .build()

        ChatUI.attachmentFactoryManager = AttachmentFactoryManager(listOf(DateAttachmentFactory()))

        ChatUI.attachmentPreviewFactoryManager = AttachmentPreviewFactoryManager(
            listOf(
                DateAttachmentPreviewFactory(),
                ImageAttachmentPreviewFactory(),
                FileAttachmentPreviewFactory()
            )
        )

        ChatUI.quotedAttachmentFactoryManager = QuotedAttachmentFactoryManager(
            listOf(
                QuotedDateAttachmentFactory(),
                DefaultQuotedAttachmentMessageFactory()
            )
        )
    }

    private fun connectUser() {
        ChatClient.instance().connectUser(
            user = User(
                id = "filip",
                extraData = mutableMapOf(
                    "name" to "Filip Babić",
                    "image" to "https://ca.slack-edge.com/T02RM6X6B-U022AFX9D2S-f7bcb3d56180-128"
                )
            ),
            token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyX2lkIjoiZmlsaXAifQ.WKqTjU6fHHjtFej-sUqS2ml3Rvdqn4Ptrf7jfKqzFgU"
        ).enqueue()
    }
}
