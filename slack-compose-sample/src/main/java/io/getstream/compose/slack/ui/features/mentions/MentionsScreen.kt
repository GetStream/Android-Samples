package io.getstream.compose.slack.ui.features.mentions

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import io.getstream.chat.android.compose.ui.theme.ChatTheme
import io.getstream.compose.slack.shapes

/** [WIP]
 * A screen component to represent mentions or reaction screen
 *
 */
@Composable
fun MentionsScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(ChatTheme.colors.appBackground)
            .wrapContentSize(Alignment.Center)
    ) {
        Text(
            text = "Mention's View",
            fontWeight = FontWeight.Bold,
            color = ChatTheme.colors.textHighEmphasis,
            modifier = Modifier.align(Alignment.CenterHorizontally),
            textAlign = TextAlign.Center,
            fontSize = 25.sp
        )
    }
}

@Preview(showBackground = true)
@Composable
fun MentionsScreenPreview() {
   ChatTheme(shapes = shapes()) {
        MentionsScreen()
    }
}
