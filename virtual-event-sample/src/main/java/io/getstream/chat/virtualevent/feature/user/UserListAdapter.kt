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

package io.getstream.chat.virtualevent.feature.user

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import io.getstream.chat.android.client.models.User
import io.getstream.chat.virtualevent.databinding.ItemUserBinding

class UserListAdapter(
    private val userClickListener: (user: User) -> Unit,
) : RecyclerView.Adapter<UserListAdapter.UserViewHolder>() {

    private val users: MutableList<User> = mutableListOf()

    fun setUsers(users: List<User>) {
        this.users.clear()
        this.users.addAll(users)
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = users.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        return ItemUserBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
            .let { UserViewHolder(it, userClickListener) }
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.bind(users[position])
    }

    class UserViewHolder(
        private val binding: ItemUserBinding,
        private val userClickListener: (user: User) -> Unit,
    ) : RecyclerView.ViewHolder(binding.root) {

        lateinit var user: User

        init {
            binding.root.setOnClickListener {
                userClickListener(user)
            }
        }

        fun bind(user: User) {
            this.user = user
            with(binding) {
                userAvatarView.setUserData(user)
                nameTextView.text = user.name
            }
        }
    }
}
