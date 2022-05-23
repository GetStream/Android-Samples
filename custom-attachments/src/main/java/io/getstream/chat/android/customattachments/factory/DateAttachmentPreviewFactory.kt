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
        parent: ViewGroup,
    ): BaseSelectedCustomAttachmentViewHolder {
        return ItemDateAttachmentPreviewBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
            .let(::DateAttachmentPreviewViewHolder)
    }

    class DateAttachmentPreviewViewHolder(
        private val binding: ItemDateAttachmentPreviewBinding,
    ) : BaseSelectedCustomAttachmentViewHolder(binding.root) {

        override fun bind(attachment: Attachment, onAttachmentCancelled: (Attachment) -> Unit) {
            binding.dateTextView.text = attachment.extraData["payload"].toString()
            binding.deleteButton.setOnClickListener {
                onAttachmentCancelled(attachment)
            }
        }
    }
}
