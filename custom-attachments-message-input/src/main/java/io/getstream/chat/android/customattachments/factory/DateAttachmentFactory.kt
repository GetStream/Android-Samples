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

package io.getstream.chat.android.customattachments.factory

import android.view.LayoutInflater
import android.view.ViewGroup
import io.getstream.chat.android.customattachments.databinding.ItemDateAttachmentBinding
import io.getstream.chat.android.models.Message
import io.getstream.chat.android.ui.feature.messages.list.adapter.MessageListListenerContainer
import io.getstream.chat.android.ui.feature.messages.list.adapter.viewholder.attachment.AttachmentFactory
import io.getstream.chat.android.ui.feature.messages.list.adapter.viewholder.attachment.InnerAttachmentViewHolder

class DateAttachmentFactory : AttachmentFactory {

    override fun canHandle(message: Message): Boolean {
        return message.attachments.any { it.type == "date" }
    }

    override fun createViewHolder(
        message: Message,
        listeners: MessageListListenerContainer?,
        parent: ViewGroup
    ): InnerAttachmentViewHolder {
        return ItemDateAttachmentBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
            .let { DateAttachmentViewHolder(it, listeners) }
    }

    class DateAttachmentViewHolder(
        private val binding: ItemDateAttachmentBinding,
        listeners: MessageListListenerContainer?
    ) : InnerAttachmentViewHolder(binding.root) {

        private lateinit var message: Message

        init {
            binding.dateTextView.setOnClickListener {
                listeners?.attachmentClickListener?.onAttachmentClick(
                    message,
                    message.attachments.first()
                )
            }
            binding.dateTextView.setOnLongClickListener {
                listeners?.messageLongClickListener?.onMessageLongClick(message)
                true
            }
        }

        override fun onBindViewHolder(message: Message) {
            this.message = message

            binding.dateTextView.text = message.attachments
                .first { it.type == "date" }
                .extraData["payload"]
                .toString()
        }
    }
}
