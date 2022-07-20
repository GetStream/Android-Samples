package io.getstream.chat.android.customattachments

import android.widget.ImageView
import com.getstream.sdk.chat.adapter.MessageListItem
import com.getstream.sdk.chat.enums.GiphyAction
import com.getstream.sdk.chat.enums.OnlineStatus
import com.getstream.sdk.chat.navigation.ChatNavigationHandler
import com.getstream.sdk.chat.navigation.ChatNavigator
import com.getstream.sdk.chat.navigation.destinations.ChatDestination
import io.getstream.chat.android.client.models.Message
import java.util.*

fun testImports(){

    val dateSeparator = MessageListItem.DateSeparatorItem(Date())
    dateSeparator.getStableId()
    val loadingMoreIndicator = MessageListItem.LoadingMoreIndicatorItem
    val position = MessageListItem.Position.BOTTOM
    val messageItem = MessageListItem.MessageItem(Message())
    val threadSeparator = MessageListItem.ThreadSeparatorItem(Date(),0)
    val typingItem = MessageListItem.TypingItem(listOf())

    GiphyAction.CANCEL

    OnlineStatus.CONNECTED

    ChatNavigationHandler { TODO("Not yet implemented") }

    ChatNavigator{ }


}