package io.getstream.compose.slack.ui.features.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.getstream.chat.android.compose.ui.theme.ChatTheme

/**
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
            }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ProfileScreenPreview() {
    ChatTheme {
        ProfileScreen()
    }
}
