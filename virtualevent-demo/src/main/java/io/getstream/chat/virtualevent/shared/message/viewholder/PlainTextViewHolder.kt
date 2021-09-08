package io.getstream.chat.virtualevent.shared.message.viewholder

import android.view.LayoutInflater
import android.view.ViewGroup
import com.getstream.sdk.chat.adapter.MessageListItem
import io.getstream.chat.android.client.models.name
import io.getstream.chat.android.ui.message.list.adapter.BaseMessageItemViewHolder
import io.getstream.chat.android.ui.message.list.adapter.MessageListItemPayloadDiff
import io.getstream.chat.virtualevent.databinding.ItemMessagePlainTextBinding

class PlainTextViewHolder(
    parentView: ViewGroup,
    private val binding: ItemMessagePlainTextBinding = ItemMessagePlainTextBinding.inflate(
        LayoutInflater.from(
            parentView.context
        ),
        parentView,
        false
    ),
) : BaseMessageItemViewHolder<MessageListItem.MessageItem>(binding.root) {

    override fun bindData(data: MessageListItem.MessageItem, diff: MessageListItemPayloadDiff?) {
        binding.messageText.text = data.message.text
        binding.username.text = data.message.user.name
        binding.avatarView.setUserData(data.message.user)
    }
}
