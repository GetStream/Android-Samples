package io.getstream.chat.android.compose.customattachments.messages.ui

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import io.getstream.chat.android.compose.customattachments.R
import io.getstream.chat.android.compose.ui.messages.composer.MessageComposer
import io.getstream.chat.android.compose.ui.theme.ChatTheme
import io.getstream.chat.android.compose.viewmodel.messages.MessageComposerViewModel

/**
 * A custom [MessageComposer] with a button to trigger the visibility of [CreateEventDialog].
 *
 * @param viewModel The ViewModel that provides pieces of data to show in the composer, like the
 * currently selected integration data or the user input. It also handles sending messages.
 * @param onCreateEventClick Handler for the create event integration.
 */
@Composable
fun CustomMessageComposer(
    viewModel: MessageComposerViewModel,
    onCreateEventClick: () -> Unit,
) {
    MessageComposer(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        viewModel = viewModel,
        integrations = {
            IconButton(
                modifier = Modifier
                    .size(48.dp)
                    .padding(12.dp),
                content = {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_calendar),
                        contentDescription = null,
                        tint = ChatTheme.colors.textLowEmphasis,
                    )
                },
                onClick = onCreateEventClick
            )
        },
    )
}
