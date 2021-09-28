package io.getstream.videochat.video

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.ColorInt
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.CircleCropTransformation
import io.getstream.videochat.R
import kotlin.properties.Delegates

class MessageListAdapter : RecyclerView.Adapter<MessageListAdapter.MessageViewHolder>() {
    private var messages: List<MessageViewHolder.Message> by Delegates.observable(listOf()) { _, _, newMessages ->
        asyncListDiffer.submitList(newMessages.takeIf { it.isNotEmpty() })
    }
    private val asyncListDiffer: AsyncListDiffer<MessageViewHolder.Message> by lazy {
        AsyncListDiffer(
            this,
            object : DiffUtil.ItemCallback<MessageViewHolder.Message>() {
                override fun areContentsTheSame(
                    oldItem: MessageViewHolder.Message,
                    newItem: MessageViewHolder.Message
                ): Boolean = oldItem == newItem

                override fun areItemsTheSame(
                    oldItem: MessageViewHolder.Message,
                    newItem: MessageViewHolder.Message
                ): Boolean = oldItem.id == newItem.id
            }
        )
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageViewHolder {
        return LayoutInflater
            .from(parent.context).inflate(R.layout.item_message, parent, false)
            .let { MessageViewHolder(it) }
    }

    override fun getItemCount(): Int = asyncListDiffer.currentList.size

    override fun onBindViewHolder(holder: MessageViewHolder, position: Int) {
        holder.bind(asyncListDiffer.currentList[position])
    }

    fun addMessages(newMessages: List<MessageViewHolder.Message>) {
        messages = (messages + newMessages).sortedBy { it.timestamp }
    }

    class MessageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val avatarImageView: ImageView by lazy { itemView.findViewById(R.id.avatarImageView) }
        private val userNameTextView: TextView by lazy { itemView.findViewById(R.id.userNameTextView) }
        private val messageTextView: TextView by lazy { itemView.findViewById(R.id.messageTextView) }

        fun bind(message: Message) {
            userNameTextView.text = message.userName
            userNameTextView.setTextColor(message.colorName)
            messageTextView.text = message.text
            avatarImageView.load(message.avatarUrl) {
                transformations(CircleCropTransformation())
            }
        }

        data class Message(
            val id: String,
            val timestamp: Long,
            val avatarUrl: String,
            val userName: String,
            @ColorInt val colorName: Int,
            val text: String
        )
    }
}
