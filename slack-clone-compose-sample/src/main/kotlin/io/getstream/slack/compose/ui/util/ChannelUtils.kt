package io.getstream.slack.compose.ui.util

import io.getstream.chat.android.client.ChatClient
import io.getstream.chat.android.client.models.Channel
import io.getstream.chat.android.client.models.User

/**
 * Checks if the channel is distinct.
 *
 * A distinct channel is a channel created without ID based on members. Internally
 * the server creates a CID which starts with "!members" prefix and is unique for
 * this particular group of users.
 */
fun Channel.isDirectMessaging(): Boolean = cid.contains("!members")

/**
 * Checks if the channel is a direct conversation between the current user and some
 * other user.
 */
fun Channel.isOneToOne(): Boolean {
    return isDirectMessaging() && members.size == 2 && includesCurrentUser()
}

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