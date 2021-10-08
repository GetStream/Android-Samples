package io.getstream.slack.compose.ui.channels

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import io.getstream.chat.android.client.ChatClient
import io.getstream.chat.android.client.api.models.QuerySort
import io.getstream.chat.android.client.models.Channel
import io.getstream.chat.android.client.models.Filters
import io.getstream.chat.android.compose.ui.theme.ChatTheme
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
            )
        )
    }

    private val listViewModel: ChannelListViewModel by viewModels(::factory)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SlackTheme {
                SetupSystemUI()
                ChannelsScreen(
                    listViewModel = listViewModel,
                    workspace = sampleWorkspace,
                    onItemClick = ::openMessages
                )
            }
        }
    }

    /**
     * Responsible for updating the system UI.
     */
    @Composable
    private fun SetupSystemUI() {
        val systemUiController = rememberSystemUiController()

        val statusBarColor = ChatTheme.colors.barsBackground
        val navigationBarColor = ChatTheme.colors.appBackground

        SideEffect {
            systemUiController.setStatusBarColor(
                color = statusBarColor,
                darkIcons = false
            )
            systemUiController.setNavigationBarColor(
                color = navigationBarColor,
                darkIcons = false
            )
        }
    }

    private fun openMessages(channel: Channel) {
        startActivity(MessagesActivity.getIntent(this, channel.cid))
    }

    companion object {
        /**
         * For the sake of this sample app, the workspace is hardcoded.
         */
        private val sampleWorkspace = Workspace("getstream", R.drawable.ic_stream_logo)
    }
}
