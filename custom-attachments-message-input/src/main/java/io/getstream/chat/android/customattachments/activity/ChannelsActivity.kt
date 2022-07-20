package io.getstream.chat.android.customattachments.activity

import android.graphics.Canvas
import android.graphics.Paint
import com.getstream.sdk.chat.enums.GiphyAction
import com.getstream.sdk.chat.navigation.destinations.ChatDestination
import com.getstream.sdk.chat.utils.GridSpacingItemDecoration
import com.getstream.sdk.chat.utils.LegacyDateFormatter
import com.getstream.sdk.chat.utils.StartStopBuffer
import com.getstream.sdk.chat.utils.Utils.getApplicationName
import com.getstream.sdk.chat.utils.Utils.hideSoftKeyboard
import com.getstream.sdk.chat.utils.Utils.isSVGImage
import com.getstream.sdk.chat.utils.roundedImageView.PorterImageView
import com.getstream.sdk.chat.utils.roundedImageView.PorterShapeImageView
import com.getstream.sdk.chat.utils.strings.ChatStrings
import com.getstream.sdk.chat.utils.strings.ChatStringsImpl
import com.getstream.sdk.chat.view.EndlessMessageListScrollListener
import com.getstream.sdk.chat.view.EndlessScrollListener
import com.getstream.sdk.chat.view.messages.MessageListItemWrapper
import com.getstream.sdk.chat.viewmodel.MessageInputViewModel
import com.getstream.sdk.chat.viewmodel.messages.MessageListViewModel
import io.getstream.chat.android.client.ChatClient
import io.getstream.chat.android.client.errors.ChatError
import io.getstream.chat.android.client.models.Channel
import io.getstream.chat.android.client.models.Message
import io.getstream.chat.android.client.models.User
import io.getstream.chat.android.ui.channel.ChannelListActivity
import io.getstream.chat.android.ui.channel.ChannelListFragment
import java.util.*

class ChannelsActivity : ChannelListActivity(), ChannelListFragment.ChannelListItemClickListener {

    override fun onChannelClick(channel: Channel) {
        startActivity(MessagesActivity.createIntent(this, channel.cid))
    }

    private fun testImports(){

        object: ChatDestination(applicationContext){
            override fun navigate() {
                TODO("Not yet implemented")
            }
        }

        GridSpacingItemDecoration(0, 0, false)

        object: LegacyDateFormatter(){
            override fun formatDate(date: Date?): String {
                TODO("Not yet implemented")
            }

            override fun formatTime(time: Date?): String {
                TODO("Not yet implemented")
            }
        }

        StartStopBuffer<String>()

        hideSoftKeyboard(applicationContext)
        getApplicationName(applicationContext)
        isSVGImage("")

        object: PorterImageView(applicationContext){
            override fun paintMaskCanvas(maskCanvas: Canvas?, maskPaint: Paint?, width: Int, height: Int) {
                TODO("Not yet implemented")
            }
        }

        object: PorterShapeImageView(applicationContext){}

        object : ChatStrings {
            override fun get(resId: Int): String {
                TODO("Not yet implemented")
            }

            override fun get(resId: Int, vararg formatArgs: Any?): String {
                TODO("Not yet implemented")
            }
        }

        ChatStringsImpl(applicationContext)

        EndlessMessageListScrollListener(0, {}){}

        EndlessScrollListener(0){}

        MessageListItemWrapper()

        MessageInputViewModel("")

        MessageListViewModel("")

        MessageListViewModel.DateSeparatorHandler { previousMessage, message -> true }

        MessageListViewModel.ErrorEvent.BlockUserError(ChatError())
        MessageListViewModel.ErrorEvent.FlagMessageError(ChatError())
        MessageListViewModel.ErrorEvent.MuteUserError(ChatError())
        MessageListViewModel.ErrorEvent.PinMessageError(ChatError())
        MessageListViewModel.ErrorEvent.UnmuteUserError(ChatError())
        MessageListViewModel.ErrorEvent.UnpinMessageError(ChatError())

        MessageListViewModel.Event.BlockUser(User(), "")
        MessageListViewModel.Event.BottomEndRegionReached("")
        MessageListViewModel.Event.DeleteMessage(Message())
        MessageListViewModel.Event.DownloadAttachment{ ChatClient.instance().unbanUser("","","")}
        MessageListViewModel.Event.EndRegionReached
        MessageListViewModel.Event.FlagMessage(Message())
        MessageListViewModel.Event.LastMessageRead


        MessageListViewModel.Event.GiphyActionSelected(message = Message(),GiphyAction.CANCEL)


        MessageListViewModel.MessagePositionHandler { prevMessage, message, nextMessage, isAfterDateSeparator -> TODO("Not yet implemented") }

        MessageListViewModel.Mode.Thread(Message())

        MessageListViewModel.State.Loading
        MessageListViewModel.State.NavigateUp
        MessageListViewModel.State.Result(MessageListItemWrapper())
    }
}
