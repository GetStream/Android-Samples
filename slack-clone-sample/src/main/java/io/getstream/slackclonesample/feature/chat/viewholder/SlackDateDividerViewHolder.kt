package io.getstream.slackclonesample.feature.chat.viewholder

import android.text.format.DateUtils
import android.view.ContextThemeWrapper
import android.view.LayoutInflater
import android.view.ViewGroup
import com.getstream.sdk.chat.adapter.MessageListItem
import io.getstream.chat.android.ui.R
import io.getstream.chat.android.ui.message.list.adapter.BaseMessageItemViewHolder
import io.getstream.chat.android.ui.message.list.adapter.MessageListItemPayloadDiff
import io.getstream.slackclonesample.databinding.ItemDateDividerBinding

class SlackDateDividerViewHolder(
    private val parentView: ViewGroup,
    private val binding: ItemDateDividerBinding = ItemDateDividerBinding.inflate(
        LayoutInflater.from(
            ContextThemeWrapper(parentView.context, R.style.StreamUiTheme)
        ),
        parentView,
        false
    ),
) : BaseMessageItemViewHolder<MessageListItem.DateSeparatorItem>(binding.root) {

    override fun bindData(data: MessageListItem.DateSeparatorItem, diff: MessageListItemPayloadDiff?) {
        with(binding) {
            dateLabel.text =
                DateUtils.getRelativeTimeSpanString(
                    data.date.time,
                    System.currentTimeMillis(),
                    DateUtils.DAY_IN_MILLIS,
                    DateUtils.FORMAT_ABBREV_RELATIVE
                )

        }
    }
}