package io.getstream.compose.slack.ui.features.messaging

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import io.getstream.chat.android.compose.ui.theme.ChatTheme
import io.getstream.compose.slack.R

/**
 * A simple customizable component to represent our messaging screen header view.
 *
 * @param modifier - for styling this view component.
 * @param title - current selected channel name to load the header text component.
 * @param subTitle - header sub-title text content, can be nullable (default null).
 * @param onClickedHandler - a click handler to handle clicks when this view is clicked
 */
@Composable
fun MessageHeaderView(
    modifier: Modifier = Modifier,
    title: String,
    subTitle: String? = null,
    onClickedHandler: () -> Unit = {},
) {
    Column(
        modifier = modifier.clickable(
            interactionSource = remember { MutableInteractionSource() },
            indication = null
        ) {
            onClickedHandler()
        }
    ) {
        // Title component
        Text(
            text = title,
            style = ChatTheme.typography.title3Bold,
            color = ChatTheme.colors.textHighEmphasis
        )

        // Sub-title text component
        Text(
            text = subTitle ?: stringResource(id = R.string.details),
            style = ChatTheme.typography.body,
            color = ChatTheme.colors.textLowEmphasis
        )
    }
}
