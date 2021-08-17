package io.getstream.slackclonesample.feature.chat.viewholder

import android.graphics.Color
import android.view.ContextThemeWrapper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.core.view.updateLayoutParams
import com.getstream.sdk.chat.adapter.MessageListItem
import io.getstream.chat.android.client.models.name
import io.getstream.chat.android.ui.ChatUI
import io.getstream.chat.android.ui.R
import io.getstream.chat.android.ui.common.markdown.ChatMarkdown
import io.getstream.chat.android.ui.message.list.adapter.BaseMessageItemViewHolder
import io.getstream.chat.android.ui.message.list.adapter.MessageListItemPayloadDiff
import io.getstream.slackclonesample.databinding.ItemMessageListBinding

class SlackPlainTextViewHolder(
    private val parentView: ViewGroup,
    private val binding: ItemMessageListBinding = ItemMessageListBinding.inflate(
        LayoutInflater.from(
            ContextThemeWrapper(parentView.context, R.style.StreamUiTheme)
        ),
        parentView,
        false
    ),
) : BaseMessageItemViewHolder<MessageListItem.MessageItem>(binding.root) {

    private val markdown: ChatMarkdown by lazy { ChatUI.markdown }

    override fun bindData(data: MessageListItem.MessageItem, diff: MessageListItemPayloadDiff?) {
        with(binding) {
            markdown.setText(messageText, data.message.text)
            decoratePlainText(data, messageText)

            if (MessageListItem.Position.TOP in data.positions) {
                avatarView.isVisible = true
                avatarView.setUserData(data.message.user)
            } else {
                avatarView.isVisible = false
            }

            if (MessageListItem.Position.TOP in data.positions) {
                userNameTextView.text = data.message.user.name
            } else {
                userNameTextView.visibility = View.GONE
                messageText.updateLayoutParams<RelativeLayout.LayoutParams> {
                    this.marginStart = parentView.context.resources.getDimensionPixelSize(R.dimen.stream_ui_avatar_size_medium)
                }
            }

        }
    }

    private fun decoratePlainText(data: MessageListItem.MessageItem, messageText: TextView) {
        messageText.setTextColor(Color.BLACK)
    }
}