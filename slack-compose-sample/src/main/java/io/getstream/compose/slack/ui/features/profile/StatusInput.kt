package io.getstream.compose.slack.ui.features.profile

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.SentimentSatisfiedAlt
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusEvent
import androidx.compose.ui.res.stringResource
import io.getstream.chat.android.compose.ui.common.InputField
import io.getstream.chat.android.compose.ui.theme.ChatTheme
import io.getstream.compose.slack.R

/**
 * The status input component that allows the user to fill in a status message.
 *
 * @param modifier - Modifier for styling.
 * @param input - Current input status value, by default empty.
 * @param onValueChange - Handler when the value changes.
 * @param leadingIcon - The icon at the start of the status input component that's customizable, but shows
 * [DefaultStatusLeadingIcon] by default.
 * @param label - The label shown in the status input field component, when there's no input.
 * */
@Composable
fun StatusInput(
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    input: String = "",
    onSearchStarted: () -> Unit = {},
    leadingIcon: @Composable RowScope.() -> Unit = { DefaultStatusLeadingIcon() },
    label: @Composable () -> Unit = { DefaultStatusLabel() },
) {
    var isFocused by remember { mutableStateOf(false) }

    InputField(
        modifier = modifier
            .onFocusEvent { newState ->
                val wasPreviouslyFocused = isFocused

                if (!wasPreviouslyFocused && newState.isFocused) {
                    onSearchStarted()
                }

                isFocused = newState.isFocused
            },
        value = input,
        onValueChange = onValueChange,
        decorationBox = { innerTextField ->
            Row(
                Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                leadingIcon()

                Box(modifier = Modifier.weight(8f)) {
                    if (input.isEmpty()) {
                        label()
                    }

                    innerTextField()
                }
            }
        }
    )
}

/**
 * Default status field leading "emoticon" icon.
 * */
@Composable
internal fun RowScope.DefaultStatusLeadingIcon() {
    Icon(
        modifier = Modifier
            .weight(1f)
            .clickable {
                /* to add any action if needed */
            },
        imageVector = Icons.Default.SentimentSatisfiedAlt,
        contentDescription = stringResource(id = R.string.accessibility_status_icon),
        tint = ChatTheme.colors.textHighEmphasis
    )
}

/**
 * Default status input field label.
 * */
@Composable
internal fun DefaultStatusLabel() {
    Text(
        text = stringResource(id = R.string.status_input_label),
        style = ChatTheme.typography.body,
        color = ChatTheme.colors.textLowEmphasis,
    )
}
