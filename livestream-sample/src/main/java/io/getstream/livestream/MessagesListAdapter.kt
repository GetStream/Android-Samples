package io.getstream.livestream

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import io.getstream.chat.android.client.models.Message
import kotlinx.android.synthetic.main.item_message.view.*

class MessagesListAdapter() : ListAdapter<Message, MessageViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_message, parent, false)
        return MessageViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MessageViewHolder, position: Int) {
        holder.bindMessage(getItem(position))
    }

    companion object {
        val DiffCallback = object : DiffUtil.ItemCallback<Message>() {
            override fun areItemsTheSame(oldItem: Message, newItem: Message): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Message, newItem: Message): Boolean {
                return oldItem == newItem
            }
        }
    }
}

class MessageViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    fun bindMessage(message: Message) {
        message.apply {
            itemView.avatar.loadUrl(user.image, R.drawable.ic_person_white_24dp)
            itemView.userName.text = user.name
            itemView.message.text = text
        }
    }
}
