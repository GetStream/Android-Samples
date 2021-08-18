package io.getstream.compose.slack.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.getstream.chat.android.compose.ui.theme.ChatTheme
import io.getstream.compose.slack.presentation.components.FullAvatar
import io.getstream.compose.slack.presentation.components.StatusInput

/** [WIP]
 * A screen component to represent profile or a settings page for the slack workspace example.
 *
 */
@Composable
fun ProfileScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(ChatTheme.colors.appBackground)
    ) {
        FullAvatar(
            modifier = Modifier.padding(start = 16.dp, top = 16.dp) // design baseline
        )
        StatusInput(
            modifier = Modifier
                .padding(16.dp)
                .height(48.dp),
            onValueChange = { _ ->
                /* Handle action on value change on status input field */
            })
    }
}

@Preview(showBackground = true)
@Composable
fun ProfileScreenPreview() {
    ChatTheme {
        ProfileScreen()
    }
}
