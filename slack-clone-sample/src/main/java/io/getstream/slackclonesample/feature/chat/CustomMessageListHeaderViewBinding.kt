package io.getstream.slackclonesample.feature.chat

import android.util.Log
import androidx.lifecycle.LifecycleOwner
import com.google.android.material.appbar.MaterialToolbar
import io.getstream.chat.android.client.models.name
import io.getstream.chat.android.ui.common.extensions.getDisplayName
import io.getstream.chat.android.ui.message.list.header.viewmodel.MessageListHeaderViewModel

@JvmName("bind")
public fun MessageListHeaderViewModel.bindView(
    view: MaterialToolbar,
    lifecycle: LifecycleOwner,
    currentUserId: String
) {
    channelState.observe(lifecycle) {
        Log.d("Channel Member Count", "${it.memberCount}")
        if (it.memberCount == 2) {
            val otherUserName =
                if (it.members[0].getUserId() == currentUserId) it.members[1].user.name
                else it.members[0].user.name
            view.title = otherUserName
        } else {
            view.title = "# ${it.getDisplayName(view.context).replace(' ', '_').lowercase()}"
        }
    }
}
