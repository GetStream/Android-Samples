package io.getstream.chat.android.customattachments.factory

import android.view.LayoutInflater
import android.view.ViewGroup
import io.getstream.chat.android.client.models.Attachment
import io.getstream.chat.android.customattachments.databinding.ItemDateAttachmentPreviewBinding
import io.getstream.chat.android.ui.message.composer.attachment.AttachmentPreviewViewHolder
import io.getstream.chat.android.ui.message.composer.attachment.factory.AttachmentPreviewFactory

class DateAttachmentPreviewFactory : AttachmentPreviewFactory {

    override fun canHandle(attachment: Attachment): Boolean {
        return attachment.type == "date"
    }

    override fun onCreateViewHolder(
        parentView: ViewGroup,
        attachmentRemovalListener: (Attachment) -> Unit,
    ): AttachmentPreviewViewHolder {
        return ItemDateAttachmentPreviewBinding
            .inflate(LayoutInflater.from(parentView.context), parentView, false)
            .let { DateAttachmentPreviewViewHolder(it, attachmentRemovalListener) }
    }

    class DateAttachmentPreviewViewHolder(
        private val binding: ItemDateAttachmentPreviewBinding,
        private val attachmentRemovalListener: (Attachment) -> Unit,
    ) : AttachmentPreviewViewHolder(binding.root) {

        private lateinit var attachment: Attachment

        init {
            binding.deleteButton.setOnClickListener {
                attachmentRemovalListener(attachment)
            }
        }

        override fun bind(attachment: Attachment) {
            this.attachment = attachment

            binding.dateTextView.text = attachment.extraData["payload"].toString()
        }
    }
}
