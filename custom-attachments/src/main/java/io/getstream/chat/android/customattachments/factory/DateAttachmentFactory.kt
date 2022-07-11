package io.getstream.chat.android.customattachments.factory

import android.view.LayoutInflater
import android.view.ViewGroup
import io.getstream.chat.android.client.models.Message
import io.getstream.chat.android.customattachments.databinding.ItemDateAttachmentBinding
import io.getstream.chat.android.ui.message.list.adapter.MessageListListenerContainer
import io.getstream.chat.android.ui.message.list.adapter.viewholder.attachment.AttachmentFactory
import io.getstream.chat.android.ui.message.list.adapter.viewholder.attachment.InnerAttachmentViewHolder

class DateAttachmentFactory : AttachmentFactory {

    override fun canHandle(message: Message): Boolean {
        return message.attachments.any { it.type == "date" }
    }

    override fun createViewHolder(
        message: Message,
        listeners: MessageListListenerContainer?,
        parent: ViewGroup,
    ): InnerAttachmentViewHolder {
        return ItemDateAttachmentBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
            .let { DateAttachmentViewHolder(it, listeners) }
    }

    class DateAttachmentViewHolder(
        private val binding: ItemDateAttachmentBinding,
        listeners: MessageListListenerContainer?,
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
