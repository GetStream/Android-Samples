package io.getstream.chat.virtualevent.shared.message

import android.view.ViewGroup
import com.getstream.sdk.chat.adapter.MessageListItem
import io.getstream.chat.android.ui.message.list.adapter.BaseMessageItemViewHolder
import io.getstream.chat.android.ui.message.list.adapter.MessageListItemViewHolderFactory
import io.getstream.chat.android.ui.message.list.adapter.MessageListItemViewType.PLAIN_TEXT
import io.getstream.chat.android.ui.message.list.adapter.viewholder.attachment.AttachmentViewFactory
import io.getstream.chat.virtualevent.shared.message.viewholder.PlainTextViewHolder

class LivestreamMessageViewHolderFactory : MessageListItemViewHolderFactory() {

    override fun createViewHolder(
        parentView: ViewGroup,
        viewType: Int
    ): BaseMessageItemViewHolder<out MessageListItem> {
        return when (viewType) {
            PLAIN_TEXT -> PlainTextViewHolder(parentView)
            else -> super.createViewHolder(parentView, viewType)
        }
    }
}