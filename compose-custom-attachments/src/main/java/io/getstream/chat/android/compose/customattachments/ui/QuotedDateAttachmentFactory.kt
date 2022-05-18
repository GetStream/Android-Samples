package io.getstream.chat.android.compose.customattachments.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import io.getstream.chat.android.compose.customattachments.R
import io.getstream.chat.android.compose.state.messages.attachments.AttachmentState
import io.getstream.chat.android.compose.ui.attachments.AttachmentFactory
import io.getstream.chat.android.compose.ui.theme.ChatTheme
import java.text.SimpleDateFormat
import java.util.*

/**
 * A custom [AttachmentFactory] that adds support for quoted date attachments.
 */
val quotedDateAttachmentFactory: AttachmentFactory = AttachmentFactory(
    canHandle = { attachments -> attachments.any { it.type == "date" } },
    content = @Composable { modifier, attachmentState ->
        QuotedDateAttachmentContent(
            modifier = modifier,
            attachmentState = attachmentState
        )
    }
)

/**
 * Represents the UI shown in the message list for quoted date attachments.
 *
 * @param attachmentState The state of the attachment.
 * @param modifier Modifier for styling.
 */
@Composable
fun QuotedDateAttachmentContent(
    attachmentState: AttachmentState,
    modifier: Modifier = Modifier,
) {
    val attachment = attachmentState.message.attachments.first { it.type == "date" }
    val date = attachment.extraData["payload"].toString()
    val formattedDate = StringBuilder().apply {
        val dateTime = SimpleDateFormat("MMMMM dd, yyyy", Locale.getDefault()).parse(date) ?: return@apply
        val year = Calendar.getInstance().apply {
            timeInMillis = dateTime.time
        }.get(Calendar.YEAR)
        if (Calendar.getInstance().get(Calendar.YEAR) != year) {
            append(year).append("\n")
        }
        append(date.replace(", $year", ""))
    }.toString()

    Column(
        modifier = modifier
            .padding(4.dp)
            .clip(ChatTheme.shapes.attachment)
            .background(ChatTheme.colors.infoAccent)
            .padding(8.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                modifier = Modifier.size(16.dp),
                painter = painterResource(id = R.drawable.ic_calendar),
                contentDescription = null,
                tint = ChatTheme.colors.textHighEmphasis,
            )

            Text(
                text = formattedDate,
                style = ChatTheme.typography.body,
                color = ChatTheme.colors.textHighEmphasis,
                textAlign = TextAlign.Center
            )
        }
    }
}