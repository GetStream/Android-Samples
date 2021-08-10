package io.getstream.livestream.compose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import io.getstream.chat.android.client.ChatClient
import io.getstream.chat.android.client.api.models.QuerySort
import io.getstream.chat.android.client.models.Filters
import io.getstream.chat.android.compose.ui.theme.ChatTheme
import io.getstream.chat.android.compose.viewmodel.channel.ChannelListViewModel
import io.getstream.chat.android.compose.viewmodel.channel.ChannelViewModelFactory
import io.getstream.chat.android.offline.ChatDomain

class LiveStreamHomeActivity : ComponentActivity() {
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
            var expanded by remember { mutableStateOf(false) }

            ChatTheme(isInDarkMode = false) {
                CustomHeader(
                    title = resources.getString(R.string.app_name),
                    actions = {
                        IconButton(
                            onClick = {
                                expanded = !expanded
                            }) {
                            Image(
                                painter = painterResource(id = R.drawable.ic_theme_switch),
                                contentDescription = "Switch theme",
                                modifier = Modifier
                                    .width(24.dp)
                                    .height(24.dp)

                            )
                        }
                        DropdownMenu(
                            expanded = expanded,
                            onDismissRequest = { expanded = false }
                        ) {

                            DropdownMenuItem(onClick = {
                                expanded = !expanded
                            }) {
                                Text(text = "LiveStream light theme")
                            }
                            DropdownMenuItem(onClick = {
                                expanded = !expanded
                            }) {
                                Text(text = "LiveStream dark theme")
                            }
                        }
                        IconButton(onClick = {
                            //TODO change grid toggle
                        }) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_toggle_grid),
                                contentDescription = "Switch theme",
                                tint = Color.Black
                            )
                        }
                    }
                ) {
                    LiveStreamChannels(
                        channelListViewModel = listViewModel,
                        context = this@LiveStreamHomeActivity
                    )
                }
            }
        }
    }
}
