package io.getstream.livestream

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import io.getstream.chat.android.client.models.Message
import io.getstream.livestream.databinding.ItemMessageBinding

class MessagesListAdapter() : ListAdapter<Message, MessageViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageViewHolder {
        return ItemMessageBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
            .let(::MessageViewHolder)
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

class MessageViewHolder(
    private val binding: ItemMessageBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bindMessage(message: Message) {
        message.apply {
            binding.avatar.loadUrl(user.image, R.drawable.ic_person_white_24dp)
            binding.userName.text = user.name
            binding.message.text = text
        }
    }
}
