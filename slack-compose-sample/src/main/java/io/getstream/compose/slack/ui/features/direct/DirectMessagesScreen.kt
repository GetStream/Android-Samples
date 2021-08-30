package io.getstream.compose.slack.ui.features.direct

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import io.getstream.chat.android.client.ChatClient
import io.getstream.chat.android.client.api.models.FilterObject
import io.getstream.chat.android.client.api.models.QuerySort
import io.getstream.chat.android.client.models.Channel
import io.getstream.chat.android.client.models.Filters
import io.getstream.chat.android.client.models.Message
import io.getstream.chat.android.client.models.User
import io.getstream.chat.android.compose.ui.channel.list.ChannelList
import io.getstream.chat.android.compose.ui.common.SearchInput
import io.getstream.chat.android.compose.ui.common.avatar.ChannelAvatar
import io.getstream.chat.android.compose.ui.theme.ChatTheme
import io.getstream.chat.android.compose.ui.util.getDisplayName
import io.getstream.chat.android.compose.ui.util.getLastMessagePreviewText
import io.getstream.chat.android.compose.viewmodel.channel.ChannelListViewModel
import io.getstream.chat.android.compose.viewmodel.channel.ChannelViewModelFactory
import io.getstream.chat.android.offline.ChatDomain
import io.getstream.compose.slack.R
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

/**
 * Default root Channel screen component for 1-1 chats, that provides the necessary ViewModel.
 *
 * It can be used without most parameters for default behavior, that can be tweaked if necessary.
 *
 * @param filters - Default filters for direct message channel type.
 * @param querySort - Default query sort for channels.
 * @param isShowingSearch - If we show the search input or hide it.
 * @param onItemClick - Handler for Channel item clicks.
 * */
@Composable
fun DirectMessagesScreen(
    filters: FilterObject = Filters.and(
        Filters.eq("type", "messaging"),
        Filters.eq("member_count", 2),
        Filters.`in`("members", listOf(ChatClient.instance().getCurrentUser()?.id ?: ""))
    ),
    querySort: QuerySort<Channel> = QuerySort.desc("last_updated"),
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
    var searchQuery by rememberSaveable { mutableStateOf("") }
    val currentUser by listViewModel.user.collectAsState()

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
                label = {
                    Text(
                        text = stringResource(id = R.string.label_jump_to_dm),
                        style = ChatTheme.typography.body,
                        color = ChatTheme.colors.textLowEmphasis,
                    )
                },
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
            itemContent = { channel ->
                DefaultDirectMessageItem(
                    channel = channel,
                    currentUser = currentUser,
                    onChannelClick = onItemClick
                )
            }
        )
    }
}

/**
 * A 1-1 direct message channel row component, that shows the channel in a list row and exposes single click action.
 *
 * @param modifier - For special styling, like theming.
 * @param channel - The channel data to show.
 * @param currentUser - Channel user that the current User is chatting with.
 * @param onChannelClick - Handler for a single tap on an item.
 * */
@Composable
internal fun DefaultDirectMessageItem(
    channel: Channel,
    currentUser: User?,
    onChannelClick: (Channel) -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .padding(top = 16.dp, bottom = 16.dp)
            .fillMaxWidth()
            .wrapContentHeight()
            .clickable {
                onChannelClick(channel)
            },
        verticalAlignment = Alignment.CenterVertically,
    ) {
        ChannelAvatar(
            modifier = Modifier
                .padding(start = 8.dp)
                .size(36.dp),
            channel = channel,
            currentUser = currentUser
        )

        Spacer(Modifier.width(8.dp))

        val lastMessage = channel.messages.lastOrNull()

        // Channel info - name and then last message
        Column(
            modifier = Modifier
                .weight(1f)
                .wrapContentHeight(),
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = channel.getDisplayName(),
                style = ChatTheme.typography.bodyBold,
                fontSize = 16.sp,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                color = ChatTheme.colors.textHighEmphasis,
            )

            val lastMessageText = channel.getLastMessagePreviewText(currentUser)

            if (lastMessageText.isNotEmpty()) {
                Text(
                    text = lastMessageText,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    style = ChatTheme.typography.body,
                    color = ChatTheme.colors.textLowEmphasis,
                )
            }
        }

        if (lastMessage != null) {
            Text(
                modifier = Modifier
                    .padding(start = 8.dp, end = 8.dp, bottom = 4.dp)
                    .wrapContentHeight()
                    .align(Alignment.Bottom),
                text = SimpleDateFormat("MMM dd", Locale.ENGLISH).format(
                    channel.lastUpdated ?: Date()
                ),
                fontSize = 14.sp,
                color = ChatTheme.colors.textLowEmphasis,
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultDirectMessageItemPreview() {
    ChatTheme {
        val user = User(
            id = "aditlal",
            extraData = mutableMapOf(
                "name" to "Adit Lal",
                "image" to "https://picsum.photos/id/237/200/300",
            ),
            online = true
        )

        val channel = Channel(
            cid = "test",
            extraData = mutableMapOf(
                "name" to "test",
                "image" to "https://picsum.photos/id/237/200/300"
            ),
            messages = listOf(Message(id = "test", cid = "test", text = "Yolo", user = user))
        )

        DefaultDirectMessageItem(channel = channel, currentUser = user, onChannelClick = {})
    }
}
