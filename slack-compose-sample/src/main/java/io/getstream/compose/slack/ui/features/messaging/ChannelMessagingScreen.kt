package io.getstream.compose.slack.ui.features.messaging

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import io.getstream.chat.android.client.ChatClient
import io.getstream.chat.android.client.api.models.FilterObject
import io.getstream.chat.android.client.api.models.QuerySort
import io.getstream.chat.android.client.models.Channel
import io.getstream.chat.android.client.models.Filters
import io.getstream.chat.android.compose.ui.theme.ChatTheme
import io.getstream.compose.slack.R

@Composable
fun ChannelMessagingScreen(
    modifier: Modifier = Modifier,
    filters: FilterObject = Filters.and(
        Filters.or(
            Filters.eq("type", "messaging"),
            Filters.eq("muted", true),
        ),
        Filters.greaterThanEquals("member_count", 2),
        Filters.`in`("members", listOf(ChatClient.instance().getCurrentUser()?.id ?: ""))
    ),
    querySort: QuerySort<Channel> = QuerySort.desc("member_count"),
    title: String,
    onBackPressed: () -> Unit
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = title,
                        style = ChatTheme.typography.title3Bold,
                        color = ChatTheme.colors.textHighEmphasis
                    )
                },
                backgroundColor = ChatTheme.colors.barsBackground,
                navigationIcon = {
                    IconButton(
                        modifier = Modifier.clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = rememberRipple(color = ChatTheme.colors.textHighEmphasis)
                        ) {},
                        onClick = {
                            onBackPressed()
                        }
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_left_navigation),
                            contentDescription = stringResource(id = R.string.accessibility_back),
                            tint = ChatTheme.colors.textHighEmphasis,
                        )
                    }
                },
                actions = {
                    IconButton(
                        modifier = Modifier.clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = rememberRipple(color = ChatTheme.colors.textHighEmphasis)
                        ) {},
                        onClick = {
                            // TODO add a new channel info screen
                        }
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_info),
                            contentDescription = stringResource(id = R.string.accessibility_icon),
                            tint = ChatTheme.colors.textHighEmphasis,
                        )
                    }
                }
            )
        },
    ) {

    }
}
