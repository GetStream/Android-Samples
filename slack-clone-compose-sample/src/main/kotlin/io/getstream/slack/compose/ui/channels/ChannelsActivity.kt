package io.getstream.slack.compose.ui.channels

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import io.getstream.chat.android.client.ChatClient
import io.getstream.chat.android.client.api.models.QuerySort
import io.getstream.chat.android.client.models.Channel
import io.getstream.chat.android.client.models.Filters
import io.getstream.chat.android.compose.viewmodel.channel.ChannelListViewModel
import io.getstream.chat.android.compose.viewmodel.channel.ChannelViewModelFactory
import io.getstream.chat.android.offline.ChatDomain
import io.getstream.slack.compose.R
import io.getstream.slack.compose.model.Workspace
import io.getstream.slack.compose.ui.messages.MessagesActivity
import io.getstream.slack.compose.ui.theme.SlackTheme
import io.getstream.slack.compose.ui.util.currentUserId

class ChannelsActivity : ComponentActivity() {

    private val factory by lazy {
        ChannelViewModelFactory(
            ChatClient.instance(),
            ChatDomain.instance(),
            QuerySort
                .desc<Channel>("has_unread")
                .desc("last_message_at"),
            Filters.and(
                Filters.eq("type", "messaging"),
                Filters.`in`("members", listOf(currentUserId()))
            ),
        )
    }

    private val listViewModel: ChannelListViewModel by viewModels { factory }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SlackTheme {
                ChannelsScreen(
                    listViewModel = listViewModel,
                    workspace = streamWorkspace,
                    onItemClick = ::openMessages
                )
            }
        }
    }

    private fun openMessages(channel: Channel) {
        startActivity(MessagesActivity.getIntent(this, channel.cid))
    }

    companion object {
        /**
         * For the sake of example we hardcoded the "Stream" workspace.
         */
        private val streamWorkspace = Workspace(
            title = "getstream",
            logo = R.drawable.ic_channel
        )
    }
}
