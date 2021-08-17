package io.getstream.slackclonesample.feature.chat.viewholder

import android.content.res.Resources
import android.view.ContextThemeWrapper
import android.view.LayoutInflater
import android.view.ViewGroup
import com.getstream.sdk.chat.adapter.MessageListItem
import io.getstream.chat.android.ui.R
import io.getstream.chat.android.ui.message.list.adapter.BaseMessageItemViewHolder
import io.getstream.chat.android.ui.message.list.adapter.MessageListItemPayloadDiff
import io.getstream.slackclonesample.databinding.ItemMessageGiphyBinding

class SlackGiphyViewHolder(
    private val parentView: ViewGroup,
    private val binding: ItemMessageGiphyBinding = ItemMessageGiphyBinding.inflate(
        LayoutInflater.from(
            ContextThemeWrapper(parentView.context, R.style.StreamUiTheme)
        ),
        parentView,
        false
    ),
) : BaseMessageItemViewHolder<MessageListItem.MessageItem>(binding.root) {

    private val resources: Resources = parentView.context.resources

    override fun bindData(data: MessageListItem.MessageItem, diff: MessageListItemPayloadDiff?) {
        applyStyle()
    /*    data.message
            .attachments
            .firstOrNull()
            ?.let(binding.mediaAttachmentView::showAttachment)*/

        binding.giphyQueryTextView.text = data.message
            .text
            .replace(GIPHY_PREFIX, "")
    }

    private fun applyStyle() {

        // Apply default style
        binding.apply {
            cardView.setCardBackgroundColor(resources.getColor(R.color.stream_ui_white))
            cardView.elevation = resources.getDimension(R.dimen.stream_ui_elevation_small)

            horizontalDivider.setBackgroundColor(resources.getColor(R.color.stream_ui_border))
            verticalDivider1.setBackgroundColor(resources.getColor(R.color.stream_ui_border))
            verticalDivider2.setBackgroundColor(resources.getColor(R.color.stream_ui_border))

            giphyIconImageView.setImageDrawable(resources.getDrawable(R.drawable.stream_ui_ic_giphy))
        }
    }

    private companion object {
        private const val GIPHY_PREFIX = "/giphy "
    }
}