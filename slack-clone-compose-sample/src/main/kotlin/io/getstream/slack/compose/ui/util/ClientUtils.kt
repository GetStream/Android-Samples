package io.getstream.slack.compose.ui.util

import io.getstream.chat.android.client.ChatClient

/**
 * Returns the ID of the current user or an empty string as a fallback.
 */
fun currentUserId(): String {
    return ChatClient.instance().getCurrentUser()?.id ?: ""
}