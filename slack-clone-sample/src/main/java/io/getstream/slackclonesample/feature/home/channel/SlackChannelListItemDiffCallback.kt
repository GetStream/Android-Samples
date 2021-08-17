package io.getstream.slackclonesample.feature.home.channel

import android.util.Log
import androidx.recyclerview.widget.DiffUtil
import io.getstream.chat.android.client.models.Channel
import io.getstream.chat.android.client.models.Message
import io.getstream.chat.android.client.models.User
import io.getstream.chat.android.client.models.name
import io.getstream.chat.android.offline.ChatDomain
import io.getstream.chat.android.ui.channel.list.adapter.ChannelListPayloadDiff
import io.getstream.chat.android.ui.common.extensions.getCreatedAtOrThrow
import io.getstream.chat.android.ui.common.extensions.isRegular
import io.getstream.chat.android.ui.common.extensions.isSystem

inline fun <reified AnyT> Any.cast() = this as AnyT

inline fun <reified AnyT> Any.safeCast() = this as? AnyT

fun currentUserId(): String = ChatDomain.instance().user.value?.id ?: ""

fun Channel.getUsersWithoutSelf(excludeUserId: String = currentUserId()): List<User> =
    members.map { it.user }.filterNot { it.id == excludeUserId }

fun Channel.getLastMessage(): Message? =
    messages.asSequence()
        .filter { it.createdAt != null || it.createdLocallyAt != null }
        .filter { it.deletedAt == null }
        .filter { !it.silent }
        .filter { it.user.id == ChatDomain.instance().user.value?.id || !it.shadowed }
        .filter { it.isRegular() || it.isSystem() }
        .maxByOrNull { it.getCreatedAtOrThrow() }

fun Channel.diff(other: Channel): ChannelListPayloadDiff =
    ChannelListPayloadDiff(
        nameChanged = name != other.name,
        avatarViewChanged = getUsersWithoutSelf() != other.getUsersWithoutSelf(),
        readStateChanged = read != other.read,
        lastMessageChanged = getLastMessage() != other.getLastMessage(),
        unreadCountChanged = unreadCount != other.unreadCount,
    )

object SlackChannelListItemDiffCallback : DiffUtil.ItemCallback<SlackChannelListItem>() {
    private const val TAG = "SlackChannelListItemDif"

    override fun areItemsTheSame(oldItem: SlackChannelListItem, newItem: SlackChannelListItem): Boolean {
        Log.d(TAG, "areItemsTheSame() called with: oldItem = $oldItem, newItem = $newItem")
        if (oldItem::class != newItem::class) {
            return false
        }

        return when (oldItem) {
            is SlackChannelListItem.ChannelItem -> {
                oldItem.channel.cid == newItem.safeCast<SlackChannelListItem.ChannelItem>()?.channel?.cid
            }

            else -> true
        }
    }

    override fun areContentsTheSame(oldItem: SlackChannelListItem, newItem: SlackChannelListItem): Boolean {
        // this is only called if areItemsTheSame returns true, so they must be the same class
        return when (oldItem) {
            is SlackChannelListItem.ChannelItem -> {
                oldItem
                    .channel
                    .diff(newItem.cast<SlackChannelListItem.ChannelItem>().channel)
                    .hasDifference()
                    .not()
            }

            else -> true
        }
    }

    override fun getChangePayload(oldItem: SlackChannelListItem, newItem: SlackChannelListItem): Any {
        // only called if their contents aren't the same, so they must be channel items and not loading items
        return oldItem
            .cast<SlackChannelListItem.ChannelItem>()
            .channel
            .diff(newItem.cast<SlackChannelListItem.ChannelItem>().channel)
    }
}