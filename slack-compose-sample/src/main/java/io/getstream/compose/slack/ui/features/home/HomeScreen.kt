package io.getstream.compose.slack.ui.features.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Tag
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import io.getstream.chat.android.client.ChatClient
import io.getstream.chat.android.client.api.models.FilterObject
import io.getstream.chat.android.client.api.models.QuerySort
import io.getstream.chat.android.client.models.Channel
import io.getstream.chat.android.client.models.Filters
import io.getstream.chat.android.compose.ui.channel.list.ChannelList
import io.getstream.chat.android.compose.ui.common.SearchInput
import io.getstream.chat.android.compose.ui.theme.ChatTheme
import io.getstream.chat.android.compose.ui.util.getDisplayName
import io.getstream.chat.android.compose.viewmodel.channel.ChannelListViewModel
import io.getstream.chat.android.compose.viewmodel.channel.ChannelViewModelFactory
import io.getstream.chat.android.offline.ChatDomain
import io.getstream.compose.slack.R
import io.getstream.compose.slack.ui.common.OnlineStatus

/**
 * Default root Channel screen component, that provides the necessary ViewModel.
 *
 * It can be used without most parameters for default behavior, that can be tweaked if necessary.
 *
 * @param filters - Default filters for channels.
 * @param querySort - Default query sort for channels.
 * @param isShowingSearch - If we show the search input or hide it.
 * @param onItemClick - Handler for Channel item clicks.
 * */
@Composable
fun HomeScreen(
    filters: FilterObject = Filters.and(
        Filters.or(
            Filters.eq("type", "messaging"),
            Filters.eq("muted", true),
        ),
        Filters.greaterThanEquals("member_count", 2),
        Filters.`in`("members", listOf(ChatClient.instance().getCurrentUser()?.id ?: ""))
    ),
    querySort: QuerySort<Channel> = QuerySort.desc("member_count"),
    isShowingSearch: Boolean = true,
    onItemClick: (Channel) -> Unit = {},
) {
    val listViewModel: ChannelListViewModel = viewModel(
        ChannelListViewModel::class.java,
        factory = ChannelViewModelFactory(
            ChatClient.instance(),
            ChatDomain.instance(),
            querySort,
            filters
        )
    )
    // core business logic - sections etc
    // 1-1 , draft , channels
    var searchQuery by rememberSaveable { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(ChatTheme.colors.appBackground)
            .wrapContentSize(Alignment.Center)
    ) {
        // Adds a search bar for the channel list search queries
        if (isShowingSearch) {
            SearchInput(
                modifier = Modifier
                    .padding(horizontal = 12.dp, vertical = 8.dp)
                    .fillMaxWidth(),
                query = searchQuery,
                onSearchStarted = {},
                onValueChange = {
                    searchQuery = it
                    listViewModel.setSearchQuery(it)
                },
            )
        }
        ChannelList(
            modifier = Modifier
                .fillMaxSize(),
            viewModel = listViewModel,
            onChannelClick = onItemClick,
            itemContent = {
                if (it.memberCount == 2) {
                    CustomOneOnOneChannelRow(channel = it, onChannelClick = {})
                } else {
                    CustomChannelRow(channel = it, onChannelClick = {})
                }
            }
        )
    }
}

/**
 * A group channel row component, that shows the channel in a list row and exposes single click action.
 *
 * @param modifier - For special styling, like theming.
 * @param channel - The channel data to show.
 * @param onChannelClick - Handler for a single tap on an item.
 * */
@Composable
internal fun CustomChannelRow(
    channel: Channel,
    onChannelClick: (Channel) -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .clickable { onChannelClick(channel) }
            .padding(vertical = 8.dp)
            .fillMaxWidth()
            .height(24.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Icon(
            modifier = Modifier
                .padding(horizontal = 16.dp),
            imageVector = Icons.Filled.Tag,
            contentDescription = stringResource(id = R.string.accessibility_channel_icon)
        )
        Text(
            text = channel.getDisplayName(),
            style = ChatTheme.typography.body,
            fontSize = 12.sp,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            color = ChatTheme.colors.textHighEmphasis,
        )
    }
}

/**
 * A 1-1 channel row component, that shows the channel in a list row and exposes single click action.
 *
 * @param modifier - For special styling, like theming.
 * @param channel - The channel data to show.
 * @param onChannelClick - Handler for a single tap on an item.
 * */
@Composable
internal fun CustomOneOnOneChannelRow(
    channel: Channel,
    onChannelClick: (Channel) -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .clickable { onChannelClick(channel) }
            .padding(vertical = 8.dp)
            .fillMaxWidth()
            .height(24.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        OnlineStatus(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .size(12.dp),
            isOnlineStatus = channel.members.first().user.online
        )
        Text(
            text = channel.getDisplayName(),
            style = ChatTheme.typography.body,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            color = ChatTheme.colors.textHighEmphasis,
        )
    }
}
