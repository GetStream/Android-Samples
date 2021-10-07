package io.getstream.slack.compose.ui.util

import io.getstream.chat.android.client.ChatClient
import io.getstream.chat.android.client.models.Channel
import io.getstream.chat.android.client.models.User

/**
 * Checks if the channel is a direct conversation between the current user and some
 * other user.
 *
 * A one-to-one chat is basically a corner case of a distinct channel with only 2 members.
 */
fun Channel.isDirectOneToOneChat(): Boolean {
    return isDirectGroupChat() && members.size == 2 && includesCurrentUser()
}

/**
 * Checks if the channel is distinct.
 *
 * A distinct channel is a channel created without ID based on members. Internally
 * the server creates a CID which starts with "!members" prefix and is unique for
 * this particular group of users.
 */
fun Channel.isDirectGroupChat(): Boolean = cid.contains("!members")

/**
 * Checks if the current user is among the members of this channel.
 */
private fun Channel.includesCurrentUser(): Boolean {
    val currentUserId = ChatClient.instance().getCurrentUser()?.id ?: return false
    return members.any { it.user.id == currentUserId }
}

/**
 * Returns the first user in this channel apart from the current user.
 */
fun Channel.getOtherUser(): User? {
    val currentUserId = currentUserId()
    return members.find { it.user.id != currentUserId }?.user
}
