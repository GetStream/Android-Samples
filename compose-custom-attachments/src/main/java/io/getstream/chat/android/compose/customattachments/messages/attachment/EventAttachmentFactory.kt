package io.getstream.chat.android.compose.customattachments.messages.attachment

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import io.getstream.chat.android.client.models.Attachment
import io.getstream.chat.android.compose.state.messages.attachments.AttachmentState
import io.getstream.chat.android.compose.ui.attachments.AttachmentFactory
import io.getstream.chat.android.compose.ui.components.CancelIcon
import io.getstream.chat.android.compose.ui.theme.ChatTheme

val eventAttachmentFactory: AttachmentFactory = AttachmentFactory(
    canHandle = { attachments ->
        attachments.any { it.type == "event" }
    },
    content = @Composable { modifier, attachmentState ->
        EventAttachmentContent(
            modifier = modifier,
            attachmentState = attachmentState
        )
    },
    previewContent = { modifier, attachments, onAttachmentRemoved ->
        EventAttachmentPreviewContent(
            modifier = modifier,
            attachments = attachments,
            onAttachmentRemoved = onAttachmentRemoved
        )
    },
    textFormatter = {
        "${it.title} ${it.extraData["date"]}"
    },
)

@Composable
fun EventAttachmentPreviewContent(
    attachments: List<Attachment>,
    onAttachmentRemoved: (Attachment) -> Unit,
    modifier: Modifier = Modifier,
) {
    val attachment = attachments.first { it.type == "event" }

    val eventTitle = attachment.title
    val eventDate = attachment.extraData["date"].toString()

    Box(
        modifier = modifier.clip(RoundedCornerShape(16.dp))
    ) {

        Box(
            modifier = Modifier
                .wrapContentHeight()
                .clip(RoundedCornerShape(16.dp))
                .background(color = ChatTheme.colors.barsBackground)

        ) {
            Text(
                modifier = Modifier
                    .align(Alignment.CenterStart)
                    .padding(horizontal = 16.dp, vertical = 24.dp)
                    .fillMaxWidth(),
                text = "$eventDate - $eventTitle",
                style = ChatTheme.typography.body,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                color = ChatTheme.colors.textHighEmphasis
            )

            CancelIcon(
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(4.dp),
                onClick = { onAttachmentRemoved(attachments[0]) }
            )
        }
    }
}

@Composable
fun EventAttachmentContent(
    attachmentState: AttachmentState,
    modifier: Modifier = Modifier,
) {
    val attachment = attachmentState.message.attachments.first { it.type == "event" }

    val eventTitle = attachment.title
    val eventDate = attachment.extraData["date"].toString()

    Column(
        modifier = modifier
            .padding(6.dp)
            .clip(ChatTheme.shapes.attachment)
            .background(ChatTheme.colors.linkBackground)
            .padding(8.dp)
    ) {
        Text(
            text = "Scheduled Event",
            style = ChatTheme.typography.title3,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            color = ChatTheme.colors.textHighEmphasis
        )

        Text(
            text = "Title: $eventTitle",
            style = ChatTheme.typography.title3,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            color = ChatTheme.colors.textHighEmphasis
        )

        Text(
            text = "Date: $eventDate",
            style = ChatTheme.typography.title3,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            color = ChatTheme.colors.textHighEmphasis
        )
    }
}
