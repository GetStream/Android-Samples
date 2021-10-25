package io.getstream.slack.compose.ui.channels

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import io.getstream.chat.android.client.models.Channel
import io.getstream.chat.android.compose.ui.channel.header.ChannelListHeader
import io.getstream.chat.android.compose.ui.channel.list.ChannelList
import io.getstream.chat.android.compose.ui.common.SearchInput
import io.getstream.chat.android.compose.ui.theme.ChatTheme
import io.getstream.chat.android.compose.viewmodel.channel.ChannelListViewModel
import io.getstream.slack.compose.R
import io.getstream.slack.compose.model.Workspace

@Composable
fun ChannelsScreen(
    listViewModel: ChannelListViewModel,
    workspace: Workspace,
    onItemClick: (Channel) -> Unit = {}
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
            modifier = Modifier.height(56.dp),
            currentUser = currentUser,
            title = workspace.title,
            isNetworkAvailable = isNetworkAvailable,
            leadingContent = { WorkspaceLogo(logo = workspace.logo) },
            titleContent = {
                WorkspaceTitle(
                    modifier = Modifier.weight(1f),
                    title = workspace.title
                )
            },
            trailingContent = { Spacer(Modifier.width(36.dp)) },
        )

        SearchInput(
            modifier = Modifier
                .padding(start = 16.dp, end = 16.dp, top = 16.dp, bottom = 8.dp)
                .height(44.dp)
                .fillMaxWidth()
                .shadow(elevation = 4.dp, shape = ChatTheme.shapes.inputField, clip = false),
            query = searchQuery,
            onValueChange = {
                searchQuery = it
                listViewModel.setSearchQuery(it)
            },
            leadingIcon = { Spacer(Modifier.width(16.dp)) },
            label = { SearchInputHint() }
        )

        ChannelList(
            modifier = Modifier.fillMaxSize(),
            viewModel = listViewModel,
            onChannelClick = onItemClick,
            emptyContent = { EmptyContent() },
            itemContent = { ChannelItem(it, onItemClick) }
        )
    }
}

/**
 * Component that represents the workspace switch shown in the toolbar.
 */
@Composable
private fun WorkspaceLogo(@DrawableRes logo: Int) {
    Row {
        Image(
            painter = painterResource(id = logo),
            contentDescription = null,
            modifier = Modifier
                .padding(start = 8.dp)
                .size(36.dp)
                .clip(RoundedCornerShape(8.dp)),
        )
    }
}

@Composable
internal fun WorkspaceTitle(
    title: String,
    modifier: Modifier = Modifier,
) {
    Text(
        modifier = modifier
            .wrapContentWidth(align = Alignment.Start)
            .padding(horizontal = 16.dp),
        text = title,
        style = ChatTheme.typography.title1,
        maxLines = 1,
        color = Color.White,
    )
}

/**
 * Component that represents the label shown in the search component, when there's no input.
 */
@Composable
private fun SearchInputHint() {
    Text(
        text = stringResource(id = R.string.search_input_hint),
        style = ChatTheme.typography.body,
        color = ChatTheme.colors.textLowEmphasis,
    )
}

/**
 * Component that represents the empty content if there are no channels.
 */
@Composable
private fun EmptyContent() {
    Box(
        modifier = Modifier
            .background(color = ChatTheme.colors.appBackground)
            .fillMaxSize(),
    ) {
        Text(
            modifier = Modifier
                .align(Alignment.Center),
            text = stringResource(id = R.string.search_no_results),
            style = ChatTheme.typography.bodyBold,
            color = ChatTheme.colors.textHighEmphasis,
            textAlign = TextAlign.Center
        )
    }
}
