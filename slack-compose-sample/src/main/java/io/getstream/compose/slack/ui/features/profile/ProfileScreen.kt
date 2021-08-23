package io.getstream.compose.slack.ui.features.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.EditNotifications
import androidx.compose.material.icons.filled.ManageAccounts
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.SettingsApplications
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.getstream.chat.android.compose.ui.theme.ChatTheme
import io.getstream.compose.slack.R
import io.getstream.compose.slack.shapes
import java.util.Locale


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
        val isOnline = remember {
            mutableStateOf(true)
        }
        FullAvatar(
            modifier = Modifier.padding(start = 16.dp, top = 16.dp),
            isOnlineStatus = isOnline.value
        )
        StatusInput(
            modifier = Modifier
                .padding(16.dp)
                .height(48.dp),
            onValueChange = { _ ->
                /* Handle action on value change on status input field */
            }
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = rememberRipple(color = ChatTheme.colors.textHighEmphasis)
                ) {}
                .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Filled.Notifications,
                tint = ChatTheme.colors.textHighEmphasis,
                contentDescription = stringResource(id = R.string.accessibility_icon)
            )

            Text(
                modifier = Modifier.padding(horizontal = 16.dp),
                text = stringResource(id = R.string.pause_notifications),
                color = ChatTheme.colors.textHighEmphasis,
                fontSize = 16.sp
            )
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = rememberRipple(color = ChatTheme.colors.textHighEmphasis)
                ) {
                    isOnline.value = !isOnline.value
                }
                .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Filled.ManageAccounts,
                tint = ChatTheme.colors.textHighEmphasis,
                contentDescription = stringResource(id = R.string.accessibility_icon)
            )

            Text(
                modifier = Modifier.padding(horizontal = 16.dp),
                text = stringResource(
                    id = R.string.manage_user_online_status,
                    if (isOnline.value) stringResource(
                        id = R.string.user_status_away
                    ).lowercase(Locale.ENGLISH) else stringResource(
                        id = R.string.user_status_away
                    ).lowercase(Locale.ENGLISH)
                ),
                color = ChatTheme.colors.textHighEmphasis,
                fontSize = 16.sp
            )
        }

        Divider(
            modifier = Modifier.padding(vertical = 8.dp),
            color = ChatTheme.colors.textHighEmphasis
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = rememberRipple(color = ChatTheme.colors.textHighEmphasis)
                ) {}
                .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Filled.Bookmark,
                tint = ChatTheme.colors.textHighEmphasis,
                contentDescription = stringResource(id = R.string.accessibility_icon)
            )

            Text(
                modifier = Modifier.padding(horizontal = 16.dp),
                text = stringResource(R.string.saved_items),
                color = ChatTheme.colors.textHighEmphasis,
                fontSize = 16.sp
            )
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = rememberRipple(color = ChatTheme.colors.textHighEmphasis)
                ) {}
                .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Filled.Person,
                tint = ChatTheme.colors.textHighEmphasis,
                contentDescription = stringResource(id = R.string.accessibility_icon)
            )

            Text(
                modifier = Modifier.padding(horizontal = 16.dp),
                text = stringResource(R.string.view_profile),
                color = ChatTheme.colors.textHighEmphasis,
                fontSize = 16.sp
            )
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = rememberRipple(color = ChatTheme.colors.textHighEmphasis)
                ) {}
                .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Filled.EditNotifications,
                tint = ChatTheme.colors.textHighEmphasis,
                contentDescription = stringResource(id = R.string.accessibility_icon)
            )

            Text(
                modifier = Modifier.padding(horizontal = 16.dp),
                text = stringResource(R.string.notifications),
                color = ChatTheme.colors.textHighEmphasis,
                fontSize = 16.sp
            )
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = rememberRipple(color = ChatTheme.colors.textHighEmphasis)
                ) {}
                .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Filled.SettingsApplications,
                tint = ChatTheme.colors.textHighEmphasis,
                contentDescription = stringResource(id = R.string.accessibility_icon)
            )

            Text(
                modifier = Modifier.padding(horizontal = 16.dp),
                text = stringResource(R.string.preferences),
                color = ChatTheme.colors.textHighEmphasis,
                fontSize = 16.sp
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ProfileScreenPreview() {
   ChatTheme(shapes = shapes()) {
        ProfileScreen()
    }
}
