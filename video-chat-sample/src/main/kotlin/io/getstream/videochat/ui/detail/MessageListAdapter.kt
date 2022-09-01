/*
 * Copyright 2022 Stream.IO, Inc. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.getstream.videochat.ui.detail

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.CircleCropTransformation
import io.getstream.chat.android.client.models.Message
import io.getstream.videochat.R

class MessageListAdapter : ListAdapter<Message, MessageListAdapter.MessageViewHolder>(MessageDiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageViewHolder {
        return LayoutInflater
            .from(parent.context).inflate(R.layout.item_message, parent, false)
            .let { MessageViewHolder(it) }
    }

    override fun onBindViewHolder(holder: MessageViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class MessageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val avatarImageView: ImageView by lazy { itemView.findViewById(R.id.avatarImageView) }
        private val userNameTextView: TextView by lazy { itemView.findViewById(R.id.nameTextView) }
        private val messageTextView: TextView by lazy { itemView.findViewById(R.id.messageTextView) }

        fun bind(message: Message) {
            userNameTextView.text = message.user.name
            messageTextView.text = message.text
            avatarImageView.load(message.user.image) {
                transformations(CircleCropTransformation())
            }
        }
    }

    private object MessageDiffCallback : DiffUtil.ItemCallback<Message>() {
        override fun areItemsTheSame(oldItem: Message, newItem: Message): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Message, newItem: Message): Boolean {
            return oldItem == newItem
        }
    }
}
