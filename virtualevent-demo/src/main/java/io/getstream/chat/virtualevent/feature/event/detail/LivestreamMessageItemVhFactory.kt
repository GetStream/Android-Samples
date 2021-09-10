package io.getstream.chat.virtualevent.feature.event.detail

import android.view.LayoutInflater
import android.view.ViewGroup
import com.getstream.sdk.chat.adapter.MessageListItem
import io.getstream.chat.android.client.models.name
import io.getstream.chat.android.ui.message.list.adapter.BaseMessageItemViewHolder
import io.getstream.chat.android.ui.message.list.adapter.MessageListItemPayloadDiff
import io.getstream.chat.android.ui.message.list.adapter.MessageListItemViewHolderFactory
import io.getstream.chat.android.ui.message.list.adapter.MessageListItemViewType.PLAIN_TEXT
import io.getstream.chat.virtualevent.databinding.ItemMessagePlainTextBinding

class LivestreamMessageItemVhFactory : MessageListItemViewHolderFactory() {

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

class PlainTextViewHolder(
    parentView: ViewGroup,
    private val binding: ItemMessagePlainTextBinding = ItemMessagePlainTextBinding.inflate(
        LayoutInflater.from(parentView.context),
        parentView,
        false
    ),
) : BaseMessageItemViewHolder<MessageListItem.MessageItem>(binding.root) {

    override fun bindData(data: MessageListItem.MessageItem, diff: MessageListItemPayloadDiff?) {
        binding.messageTextView.text = data.message.text
        binding.usernameTextView.text = data.message.user.name
        binding.avatarView.setUserData(data.message.user)
        // TODO: check if we can push back on this
        binding.messageTimeTextView.text = "2 mins"
    }
}
