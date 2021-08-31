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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.getstream.chat.android.client.models.User
import io.getstream.chat.android.compose.ui.theme.ChatTheme
import io.getstream.compose.slack.R
import io.getstream.compose.slack.shapes
import io.getstream.compose.slack.ui.features.profile.ProfileScreenRowIcons.BOOKMARK
import io.getstream.compose.slack.ui.features.profile.ProfileScreenRowIcons.EDIT_NOTIFICATIONS
import io.getstream.compose.slack.ui.features.profile.ProfileScreenRowIcons.EDIT_ONLINE_STATUS
import io.getstream.compose.slack.ui.features.profile.ProfileScreenRowIcons.NOTIFICATION
import io.getstream.compose.slack.ui.features.profile.ProfileScreenRowIcons.PROFILE
import io.getstream.compose.slack.ui.features.profile.ProfileScreenRowIcons.SETTINGS
import java.util.Locale

/**
 * A screen component to represent profile or a settings page for the slack workspace example.
 *
 * @param isOnline - boolean toggle to show current user online status indicator.
 * @param rowItems - List of row items to show after avatar and update status views.
 */
@Composable
fun ProfileScreen(
    isOnline: Boolean,
    rowItems: List<ProfileScreenRowItem> = listOf(
        ProfileScreenRowItem(
            icon = NOTIFICATION,
            title = stringResource(id = R.string.pause_notifications)
        ),
        ProfileScreenRowItem(
            icon = EDIT_ONLINE_STATUS,
            title = stringResource(
                id = R.string.manage_user_online_status,
                if (isOnline) stringResource(
                    id = R.string.user_status_away
                ).lowercase(Locale.ENGLISH) else stringResource(
                    id = R.string.user_status_away
                ).lowercase(Locale.ENGLISH)
            )
        ),
        ProfileScreenRowItem(
            icon = BOOKMARK,
            title = stringResource(id = R.string.saved_items)
        ),
        ProfileScreenRowItem(
            icon = PROFILE,
            title = stringResource(id = R.string.view_profile)
        ),
        ProfileScreenRowItem(
            icon = EDIT_NOTIFICATIONS,
            title = stringResource(id = R.string.notifications)
        ),
        ProfileScreenRowItem(
            icon = SETTINGS,
            title = stringResource(id = R.string.preferences)
        )
    )
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(ChatTheme.colors.appBackground)
    ) {
        FullAvatar(
            modifier = Modifier.padding(start = 16.dp, top = 16.dp),
            user = User(
                id = "aditlal",
                extraData = mutableMapOf(
                    "name" to "Adit Lal",
                    "image" to "https://picsum.photos/id/237/200/300",
                ),
                online = isOnline
            )
        )
        StatusInput(
            modifier = Modifier
                .padding(16.dp)
                .height(48.dp)
        )

        // Bottom half of the screen is list of items.
        LazyColumn {
            items(rowItems) { item ->
                ProfileScreenRowContent(rowItem = item)
            }
        }
    }
}

/**
 * Represents the content that's shown in each profile screen row.
 *
 * @param rowItem - The [ProfileScreenRowItem] that contains the row details.
 * @param modifier - Modifier for styling.
 */
@Composable
internal fun ProfileScreenRowContent(
    rowItem: ProfileScreenRowItem,
    modifier: Modifier = Modifier
) {
    val icon = when (rowItem.icon) {
        NOTIFICATION -> {
            Icons.Filled.Notifications
        }
        EDIT_ONLINE_STATUS -> {
            Icons.Filled.ManageAccounts
        }
        BOOKMARK -> {
            Icons.Filled.Bookmark
        }
        PROFILE -> {
            Icons.Filled.Person
        }
        EDIT_NOTIFICATIONS -> {
            Icons.Filled.EditNotifications
        }
        SETTINGS -> {
            Icons.Filled.SettingsApplications
        }
    }

    Row(
        modifier = modifier
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
            imageVector = icon,
            tint = ChatTheme.colors.textLowEmphasis,
            contentDescription = stringResource(id = R.string.accessibility_icon)
        )

        Text(
            modifier = Modifier.padding(horizontal = 16.dp),
            text = rowItem.title,
            color = ChatTheme.colors.textHighEmphasis,
            fontSize = 16.sp
        )
    }
}

/**
 * Represents each row item in our profile screen. Each item can be clickable.
 *
 * @param icon - The icon enum [ProfileScreenRowIcons] to show an icon for the current row.
 * @param title - A label to show the title for the current row.
 * */
data class ProfileScreenRowItem(
    val icon: ProfileScreenRowIcons,
    val title: String
)

/**
 * Represents the types of icons for each row in our profile screen.
 * */
enum class ProfileScreenRowIcons {
    NOTIFICATION,
    EDIT_ONLINE_STATUS,
    BOOKMARK,
    PROFILE,
    EDIT_NOTIFICATIONS,
    SETTINGS
}

@Preview(showBackground = true)
@Composable
fun ProfileScreenPreview() {
    ChatTheme(shapes = shapes()) {
        ProfileScreen(
            isOnline = false
        )
    }
}
