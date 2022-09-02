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
import io.getstream.chat.android.client.models.Attachment
import io.getstream.chat.android.customattachments.databinding.ItemDateAttachmentPreviewBinding
import io.getstream.chat.android.ui.message.input.attachment.selected.internal.BaseSelectedCustomAttachmentViewHolder
import io.getstream.chat.android.ui.message.input.attachment.selected.internal.SelectedCustomAttachmentViewHolderFactory

class DateAttachmentPreviewFactory : SelectedCustomAttachmentViewHolderFactory {

    override fun createAttachmentViewHolder(
        attachments: List<Attachment>,
        parent: ViewGroup
    ): BaseSelectedCustomAttachmentViewHolder {
        return ItemDateAttachmentPreviewBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
            .let(::DateAttachmentPreviewViewHolder)
    }

    class DateAttachmentPreviewViewHolder(
        private val binding: ItemDateAttachmentPreviewBinding
    ) : BaseSelectedCustomAttachmentViewHolder(binding.root) {

        override fun bind(attachment: Attachment, onAttachmentCancelled: (Attachment) -> Unit) {
            binding.dateTextView.text = attachment.extraData["payload"].toString()
            binding.deleteButton.setOnClickListener {
                onAttachmentCancelled(attachment)
            }
        }
    }
}
