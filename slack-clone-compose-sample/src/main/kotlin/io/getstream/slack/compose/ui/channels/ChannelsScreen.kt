package io.getstream.slack.compose.ui.channels

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import io.getstream.chat.android.client.models.Channel
import io.getstream.chat.android.compose.ui.channel.header.ChannelListHeader
import io.getstream.chat.android.compose.ui.channel.list.ChannelList
import io.getstream.chat.android.compose.ui.common.SearchInput
import io.getstream.chat.android.compose.ui.theme.ChatTheme
import io.getstream.chat.android.compose.viewmodel.channel.ChannelListViewModel
import io.getstream.slack.compose.R
import io.getstream.slack.compose.model.Workspace
import io.getstream.slack.compose.ui.util.isDirectMessaging
import io.getstream.slack.compose.ui.util.isOneToOne

@Composable
fun ChannelsScreen(
    listViewModel: ChannelListViewModel,
    workspace: Workspace,
    onItemClick: (Channel) -> Unit = {},
) {
    val currentUser by listViewModel.user.collectAsState()
    val isNetworkAvailable by listViewModel.isOnline.collectAsState()

    var searchQuery by rememberSaveable { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(ChatTheme.colors.appBackground)
    ) {

        ChannelListHeader(
            currentUser = currentUser,
            title = workspace.title,
            isNetworkAvailable = isNetworkAvailable,
            trailingContent = { Spacer(Modifier.width(36.dp)) },
            leadingContent = {
                Image(
                    painter = painterResource(id = workspace.logo),
                    contentDescription = null,
                    modifier = Modifier
                        .size(36.dp)
                        .clip(RoundedCornerShape(8.dp)),
                )
            }
        )

        SearchInput(
            modifier = Modifier
                .padding(horizontal = 12.dp, vertical = 8.dp)
                .fillMaxWidth(),
            query = searchQuery,
            onValueChange = {
                searchQuery = it
                listViewModel.setSearchQuery(it)
            },
            leadingIcon = { Spacer(Modifier.width(8.dp)) },
            label = {
                Text(
                    text = stringResource(id = R.string.search_input_hint),
                    style = ChatTheme.typography.body,
                    color = ChatTheme.colors.textLowEmphasis,
                )
            }
        )

        ChannelList(
            modifier = Modifier.fillMaxSize(),
            viewModel = listViewModel,
            onChannelClick = onItemClick,
            itemContent = {
                when {
                    it.isOneToOne() -> OneToOneChannelItem(
                        channel = it,
                        onChannelClick = onItemClick
                    )
                    it.isDirectMessaging() -> DirectMessagingChannelItem(
                        channel = it,
                        onChannelClick = onItemClick
                    )
                    else -> GroupChannelItem(
                        channel = it,
                        onChannelClick = onItemClick
                    )
                }
            }
        )
    }
}
