package io.getstream.chat.android.compose.customattachments.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import io.getstream.chat.android.client.models.Attachment
import io.getstream.chat.android.compose.customattachments.R
import io.getstream.chat.android.compose.state.messages.attachments.AttachmentState
import io.getstream.chat.android.compose.ui.attachments.AttachmentFactory
import io.getstream.chat.android.compose.ui.components.CancelIcon
import io.getstream.chat.android.compose.ui.theme.ChatTheme
import io.getstream.chat.android.core.ExperimentalStreamChatApi

@ExperimentalStreamChatApi
val dateAttachmentFactory: AttachmentFactory = AttachmentFactory(
    canHandle = { attachments -> attachments.any { it.type == "date" } },
    content = @Composable { modifier, attachmentState ->
        DateAttachmentContent(
            modifier = modifier,
            attachmentState = attachmentState
        )
    },
    previewContent = { modifier, attachments, onAttachmentRemoved ->
        DateAttachmentPreviewContent(
            modifier = modifier,
            attachments = attachments,
            onAttachmentRemoved = onAttachmentRemoved
        )
    },
    textFormatter = { attachment ->
        attachment.extraData["date"].toString()
    },
)

/**
 * The UI that will be shown in the message composer.
 *
 * @param attachments Selected attachments.
 * @param onAttachmentRemoved Handler when the user removes an attachment from the list.
 * @param modifier Modifier for styling.
 */
@Composable
fun DateAttachmentPreviewContent(
    attachments: List<Attachment>,
    onAttachmentRemoved: (Attachment) -> Unit,
    modifier: Modifier = Modifier,
) {
    val attachment = attachments.first { it.type == "date" }

    val date = attachment.extraData["date"].toString()

    Box(
        modifier = modifier
            .wrapContentHeight()
            .clip(RoundedCornerShape(16.dp))
            .background(color = ChatTheme.colors.barsBackground)
    ) {
        Text(
            modifier = Modifier
                .align(Alignment.CenterStart)
                .padding(16.dp)
                .fillMaxWidth(),
            text = date,
            style = ChatTheme.typography.body,
            maxLines = 1,
            color = ChatTheme.colors.textHighEmphasis
        )

        CancelIcon(
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(4.dp),
            onClick = { onAttachmentRemoved(attachment) }
        )
    }
}

/**
 * The UI that will be shown in the message list.
 *
 * @param attachmentState The state of the attachment.
 * @param modifier Modifier for styling.
 */
@Composable
fun DateAttachmentContent(
    attachmentState: AttachmentState,
    modifier: Modifier = Modifier,
) {
    val attachment = attachmentState.message.attachments.first { it.type == "date" }

    val date = attachment.extraData["date"].toString()

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(4.dp)
            .clip(ChatTheme.shapes.attachment)
            .background(ChatTheme.colors.infoAccent)
            .padding(8.dp)
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                modifier = Modifier.size(16.dp),
                painter = painterResource(id = R.drawable.ic_calendar),
                contentDescription = null,
                tint = ChatTheme.colors.textHighEmphasis,
            )

            Text(
                text = date,
                style = ChatTheme.typography.body,
                maxLines = 1,
                color = ChatTheme.colors.textHighEmphasis
            )
        }
    }
}
