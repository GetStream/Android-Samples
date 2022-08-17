package io.getstream.livestream.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import io.getstream.chat.android.client.models.Message
import io.getstream.livestream.R
import io.getstream.livestream.databinding.ItemMessageBinding

class MessageListAdapter : ListAdapter<Message, MessageListAdapter.MessageViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageViewHolder {
        return ItemMessageBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
            .let(MessageListAdapter::MessageViewHolder)
    }

    override fun onBindViewHolder(holder: MessageViewHolder, position: Int) {
        holder.bindMessage(getItem(position))
    }

    class MessageViewHolder(
        private val binding: ItemMessageBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bindMessage(message: Message) {
            binding.userNameTextView.text = message.user.name
            binding.messageTextView.text = message.text

            val avatarImageView = binding.avatarImageView
            val avatarUrl = message.user.image
            val avatarPlaceholder = R.drawable.ic_person_white_24dp

            if (avatarUrl.isNotEmpty()) {
                Picasso.get().load(avatarUrl)
                    .placeholder(avatarPlaceholder)
                    .error(avatarPlaceholder)
                    .fit()
                    .into(avatarImageView)
            } else {
                Picasso.get().cancelRequest(avatarImageView)
                avatarImageView.setImageDrawable(
                    AppCompatResources.getDrawable(
                        avatarImageView.context,
                        avatarPlaceholder
                    )
                )
            }
        }
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Message>() {
            override fun areItemsTheSame(oldItem: Message, newItem: Message): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Message, newItem: Message): Boolean {
                return oldItem == newItem
            }
        }
    }
}
