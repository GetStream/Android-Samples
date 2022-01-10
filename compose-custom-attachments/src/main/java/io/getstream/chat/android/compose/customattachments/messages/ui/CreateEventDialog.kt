package io.getstream.chat.android.compose.customattachments.messages.ui

import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.google.android.material.datepicker.MaterialDatePicker
import io.getstream.chat.android.client.models.Attachment
import io.getstream.chat.android.compose.handlers.SystemBackPressedHandler
import io.getstream.chat.android.compose.ui.theme.ChatTheme
import java.text.DateFormat
import java.util.Date

/**
 * Represents a modal dialog that allows the user to create an event.
 *
 * @param onEventCreated Handler called when a new event is created.
 * @param onDismiss Handler called when the dialog is dismissed.
 */
@Composable
fun CreateEventDialog(
    onEventCreated: (Attachment) -> Unit,
    onDismiss: () -> Unit = {},
) {
    val activity = LocalContext.current as AppCompatActivity

    var eventTitle: String by rememberSaveable { mutableStateOf("") }
    var eventDate: Date by rememberSaveable { mutableStateOf(Date()) }

    val eventDateText = DateFormat.getDateInstance().format(eventDate)

    Box(
        modifier = Modifier
            .background(ChatTheme.colors.overlay)
            .fillMaxSize()
            .clickable(
                onClick = onDismiss,
                indication = null,
                interactionSource = remember { MutableInteractionSource() }
            )
    ) {
        Card(
            modifier = Modifier
                .align(Alignment.Center)
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .clickable(
                    onClick = {},
                    indication = null,
                    interactionSource = remember { MutableInteractionSource() }
                ),
            shape = ChatTheme.shapes.attachment,
            backgroundColor = ChatTheme.colors.barsBackground
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                TextField(
                    colors = TextFieldDefaults.textFieldColors(backgroundColor = Color.White),
                    value = eventTitle,
                    onValueChange = {
                        eventTitle = it
                    },
                    textStyle = ChatTheme.typography.body,
                    label = { Text("Event title") },
                    singleLine = true
                )

                Text(
                    modifier = Modifier
                        .padding(16.dp)
                        .clickable(
                            onClick = {
                                MaterialDatePicker.Builder
                                    .datePicker()
                                    .setSelection(Date().time)
                                    .build()
                                    .apply {
                                        show(activity.supportFragmentManager, "TAG")
                                        addOnPositiveButtonClickListener {
                                            eventDate = Date(it)
                                        }
                                    }
                            },
                            indication = null,
                            interactionSource = remember { MutableInteractionSource() }
                        ),
                    text = eventDateText,
                    style = ChatTheme.typography.title3,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    color = ChatTheme.colors.textHighEmphasis
                )

                TextButton(
                    modifier = Modifier.align(alignment = Alignment.End),
                    colors = ButtonDefaults.textButtonColors(contentColor = ChatTheme.colors.primaryAccent),
                    onClick = {
                        onEventCreated(
                            Attachment(
                                type = "event",
                                title = eventTitle,
                                extraData = mutableMapOf("date" to eventDateText),
                            )

                        )
                    }
                ) {
                    Text(text = stringResource(id = io.getstream.chat.android.compose.R.string.stream_compose_ok))
                }
            }
        }
    }

    SystemBackPressedHandler(isEnabled = true) {
        onDismiss()
    }
}
