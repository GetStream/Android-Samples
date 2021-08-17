package io.getstream.slackclonesample.feature.chat.viewholder

import android.view.ViewGroup
import com.getstream.sdk.chat.adapter.MessageListItem
import io.getstream.chat.android.ui.message.list.adapter.BaseMessageItemViewHolder
import io.getstream.chat.android.ui.message.list.adapter.MessageListItemViewHolderFactory
import io.getstream.chat.android.ui.message.list.adapter.MessageListItemViewType

class SlackMessageViewHolderFactory : MessageListItemViewHolderFactory() {
    override fun createViewHolder(
        parentView: ViewGroup,
        viewType: Int,
    ): BaseMessageItemViewHolder<out MessageListItem> {
        return when (viewType) {
            MessageListItemViewType.PLAIN_TEXT -> {
                SlackPlainTextViewHolder(parentView)
            }
            MessageListItemViewType.DATE_DIVIDER -> {
                SlackDateDividerViewHolder(parentView)
            }
            MessageListItemViewType.GIPHY -> {
                SlackGiphyViewHolder(parentView)
            }
            else -> {
                super.createViewHolder(parentView, viewType)
            }
        }
    }
}