package io.getstream.compose.slack.presentation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import io.getstream.chat.android.client.models.User
import io.getstream.chat.android.client.models.name
import io.getstream.chat.android.compose.ui.common.avatar.UserAvatar
import io.getstream.chat.android.compose.ui.theme.ChatTheme
import io.getstream.compose.slack.R

/**
 * A simple component to for showing user name, online status, etc.
 *
 * @param modifier - Modifier to customize the specs on the root element.
 * @param user - Optional parameter to pass in a [User] object to bind the data for this view component.
 */
@Composable
fun FullAvatar(
    modifier: Modifier = Modifier,
    user: User = User(
        id = "1f37e58d-d8b0-476a-a4f2-f8611e0d85d9",
        extraData = mutableMapOf(
            "name" to "Jc",
            "image" to "https://firebasestorage.googleapis.com/v0/b/stream-chat-internal.appspot.com/o/users%2FJc.png?alt=media",
        ),
    )
) {
    Row(
        modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        UserAvatar(
            modifier = Modifier
                .width(56.dp)
                .height(56.dp),
            user = user
        )
        Column(modifier = Modifier.padding(horizontal = 16.dp)) {
            Text(
                text = user.name,
                style = ChatTheme.typography.title3Bold,
                color = ChatTheme.colors.textHighEmphasis
            )
            Text(
                text = stringResource(id = if (user.online) R.string.user_status_active else R.string.user_status_away),
                style = ChatTheme.typography.footnote,
                color = ChatTheme.colors.textLowEmphasis
            )
        }
    }
}
