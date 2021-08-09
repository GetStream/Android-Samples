package io.getstream.livestream.compose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import io.getstream.chat.android.client.ChatClient
import io.getstream.chat.android.client.api.models.QuerySort
import io.getstream.chat.android.client.models.Filters
import io.getstream.chat.android.compose.ui.theme.ChatTheme
import io.getstream.chat.android.compose.viewmodel.channel.ChannelListViewModel
import io.getstream.chat.android.compose.viewmodel.channel.ChannelViewModelFactory
import io.getstream.chat.android.offline.ChatDomain

class LivestreamHHomeActivity : ComponentActivity() {
    private val factory by lazy {
        ChannelViewModelFactory(
            ChatClient.instance(), ChatDomain.instance(),
            QuerySort.desc("last_updated"),
            Filters.and(
                Filters.eq("type", "livestream"),
                Filters.`in`("members", listOf(ChatClient.instance().getCurrentUser()?.id ?: ""))
            )
        )
    }

    private val listViewModel: ChannelListViewModel by viewModels { factory }

    @ExperimentalMaterialApi
    @ExperimentalFoundationApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            ChatTheme(isInDarkMode = false) {
                Scaffold(
                    topBar = {
                        TopAppBar(
                            title = {
                                Text(
                                    text = resources.getString(R.string.app_name),
                                    color = ChatTheme.colors.textHighEmphasis
                                )
                            },
                            backgroundColor = ChatTheme.colors.barsBackground,
                            contentColor = ChatTheme.colors.appBackground,
                            elevation = 12.dp
                        )
                    }, content = {
                        LiveStreamChannels(
                            channelListViewModel = listViewModel,
                            context = this@LivestreamHHomeActivity
                        )
                    })
            }
        }
    }
}
