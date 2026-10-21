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

package io.getstream.chat.android.compose.customattachments.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import io.getstream.chat.android.compose.customattachments.R
import io.getstream.chat.android.compose.ui.components.ComposerCancelIcon
import io.getstream.chat.android.compose.ui.theme.ChatComponentFactory
import io.getstream.chat.android.compose.ui.theme.ChatTheme
import io.getstream.chat.android.compose.ui.theme.CustomAttachmentContentParams
import io.getstream.chat.android.compose.ui.theme.MessageComposerAttachmentsParams
import io.getstream.chat.android.compose.ui.theme.MessageComposerLeadingContentParams
import io.getstream.chat.android.compose.ui.theme.MessageComposerQuotedMessageParams
import io.getstream.chat.android.compose.ui.theme.MessageQuotedContentParams

/**
 * A custom [ChatComponentFactory] that adds support for date attachments.
 *
 * It renders date attachments in the message list, in the composer preview and in quoted
 * messages, and replaces the composer's leading button with a calendar button.
 */
object CustomChatComponentFactory : ChatComponentFactory {

    @Composable
    override fun CustomAttachmentContent(params: CustomAttachmentContentParams) {
        if (params.state.message.attachments.any { it.type == "date" }) {
            DateAttachmentContent(
                modifier = params.modifier,
                attachmentState = params.state
            )
        }
    }

    @Composable
    override fun MessageComposerAttachments(params: MessageComposerAttachmentsParams) {
        val (dateAttachments, otherAttachments) = params.attachments.partition { it.type == "date" }

        Column(modifier = params.modifier) {
            if (dateAttachments.isNotEmpty()) {
                DateAttachmentPreviewContent(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight(),
                    attachments = dateAttachments,
                    onAttachmentRemoved = params.onAttachmentRemoved
                )
            }
            if (otherAttachments.isNotEmpty()) {
                super.MessageComposerAttachments(
                    params = params.copy(
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentHeight(),
                        attachments = otherAttachments
                    )
                )
            }
        }
    }

    @Composable
    override fun MessageQuotedContent(params: MessageQuotedContentParams) {
        if (params.message.attachments.any { it.type == "date" }) {
            QuotedDateAttachmentContent(
                modifier = params.modifier,
                message = params.message
            )
        } else {
            super.MessageQuotedContent(params)
        }
    }

    @Composable
    override fun MessageComposerQuotedMessage(params: MessageComposerQuotedMessageParams) {
        if (params.quotedMessage.attachments.any { it.type == "date" }) {
            Box(modifier = params.modifier.padding(horizontal = 8.dp)) {
                QuotedDateAttachmentContent(message = params.quotedMessage)

                ComposerCancelIcon(
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .offset(x = 4.dp, y = (-4).dp),
                    onClick = params.onCancelClick
                )
            }
        } else {
            super.MessageComposerQuotedMessage(params)
        }
    }

    @Composable
    override fun MessageComposerLeadingContent(params: MessageComposerLeadingContentParams) {
        IconButton(
            modifier = params.modifier
                .size(48.dp)
                .padding(12.dp),
            content = {
                Icon(
                    painter = painterResource(id = R.drawable.ic_calendar),
                    contentDescription = null,
                    tint = ChatTheme.colors.textSecondary
                )
            },
            onClick = params.onAttachmentsClick
        )
    }
}
